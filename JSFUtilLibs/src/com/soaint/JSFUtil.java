package com.soaint;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.context.AdfFacesContext;

/**
 * General useful static utilies for working with JSF.
 * NOTE: Updated to use JSF 1.2 ExpressionFactory.
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 * $Id: JSFUtils.java 1885 2007-06-26 00:47:41Z ralsmith $
 */
public class JSFUtil {

    private static final String NO_RESOURCE_FOUND = "Missing resource: ";

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching object (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static Object resolveExpression(final String expression) {
        final FacesContext facesContext = getFacesContext();
        final Application app = facesContext.getApplication();
        final ExpressionFactory elFactory = app.getExpressionFactory();
        final ELContext elContext = facesContext.getELContext();
        final ValueExpression valueExp =
            elFactory.createValueExpression(elContext, expression,
                                            Object.class);
        return valueExp.getValue(elContext);
    }

    public static String resolveRemoteUser() {
        final FacesContext facesContext = getFacesContext();
        final ExternalContext ectx = facesContext.getExternalContext();
        return ectx.getRemoteUser();
    }

    public static String resolveUserPrincipal() {
        final FacesContext facesContext = getFacesContext();
        final ExternalContext ectx = facesContext.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        return request.getUserPrincipal().getName();
    }

    public static Object resloveMethodExpression(final String expression,
                                                 final Class returnType,
                                                 final Class[] argTypes,
                                                 final Object[] argValues) {
        final FacesContext facesContext = getFacesContext();
        final Application app = facesContext.getApplication();
        final ExpressionFactory elFactory = app.getExpressionFactory();
        final ELContext elContext = facesContext.getELContext();
        final MethodExpression methodExpression =
            elFactory.createMethodExpression(elContext, expression, returnType,
                                             argTypes);
        return methodExpression.invoke(elContext, argValues);
    }

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching Boolean.
     * @param expression EL expression
     * @return Managed object
     */
    public static Boolean resolveExpressionAsBoolean(final String expression) {
        return (Boolean)resolveExpression(expression);
    }

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching String (or creating it).
     * @param expression EL expression
     * @return Managed object
     */
    public static String resolveExpressionAsString(final String expression) {
        return (String)resolveExpression(expression);
    }

    /**
     * Convenience method for resolving a reference to a managed bean by name
     * rather than by expression.
     * @param beanName name of managed bean
     * @return Managed object
     */
    public static Object getManagedBeanValue(final String beanName) {
        final StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        return resolveExpression(buff.toString());
    }

    /**
     * Method for setting a new object into a JSF managed bean
     * Note: will fail silently if the supplied object does
     * not match the type of the managed bean.
     * @param expression EL expression
     * @param newValue new value to set
     */
    public static void setExpressionValue(final String expression, final Object newValue) {
        final FacesContext facesContext = getFacesContext();
        final Application app = facesContext.getApplication();
        final ExpressionFactory elFactory = app.getExpressionFactory();
        final ELContext elContext = facesContext.getELContext();
        final ValueExpression valueExp =
            elFactory.createValueExpression(elContext, expression,
                                            Object.class);

        //Check that the input newValue can be cast to the property type
        //expected by the managed bean.
        //If the managed Bean expects a primitive we rely on Auto-Unboxing
        final Class bindClass = valueExp.getType(elContext);
        if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
        }
    }

    /**
     * Convenience method for setting the value of a managed bean by name
     * rather than by expression.
     * @param beanName name of managed bean
     * @param newValue new value to set
     */
    public static void setManagedBeanValue(final String beanName, final Object newValue) {
        final StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        setExpressionValue(buff.toString(), newValue);
    }

    /**
     * Convenience method for setting Session variables.
     * @param key object key
     * @param object value to store
     */

    public static void storeOnSession(final String key, final Object object) {
        final FacesContext ctx = getFacesContext();
        final Map sessionState = ctx.getExternalContext().getSessionMap();
        sessionState.put(key, object);
    }

    /**
     * Convenience method for getting Session variables.
     * @param key object key
     * @return session object for key
     */
    public static Object getFromSession(final String key) {
        final FacesContext ctx = getFacesContext();
        final Map sessionState = ctx.getExternalContext().getSessionMap();
        return sessionState.get(key);
    }

    public static String getFromHeader(final String key) {
        final FacesContext ctx = getFacesContext();
        final ExternalContext ectx = ctx.getExternalContext();
        return ectx.getRequestHeaderMap().get(key);
    }

    /**
     * Convenience method for getting Request variables.
     * @param key object key
     * @return session object for key
     */
    public static Object getFromRequest(final String key) {
        final FacesContext ctx = getFacesContext();
        final Map sessionState = ctx.getExternalContext().getRequestMap();
        return sessionState.get(key);
    }

    /**
     * Pulls a String resource from the property bundle that
     * is defined under the application <message-bundle> element in
     * the faces config. Respects Locale
     * @param key string message key
     * @return Resource value or placeholder error String
     */
    public static String getStringFromBundle(final String key) {
        final ResourceBundle bundle = getBundle();
        return getStringSafely(bundle, key, null);
    }

    /**
     * Convenience method to construct a FacesMesssage
     * from a defined error key and severity
     * This assumes that the error keys follow the convention of
     * using _detail for the detailed part of the
     * message, otherwise the main message is returned for the
     * detail as well.
     * @param key for the error message in the resource bundle
     * @param severity severity of message
     * @return Faces Message object
     */
    public static FacesMessage getMessageFromBundle(final String key,
                                                    final FacesMessage.Severity severity) {
        final ResourceBundle bundle = getBundle();
        final String summary = getStringSafely(bundle, key, null);
        final String detail = getStringSafely(bundle, key + "_detail", summary);
        final FacesMessage message = new FacesMessage(summary, detail);
        message.setSeverity(severity);
        return message;
    }

    /**
     * Add JSF info message.
     * @param msg info message string
     */
    public static void addFacesInformationMessage(final String msg) {
        final FacesContext ctx = getFacesContext();
        final FacesMessage fm =
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        ctx.addMessage(getRootViewComponentId(), fm);
    }

    /**
     * Add JSF error message.
     * @param msg error message string
     */
    public static void addFacesErrorMessage(final String msg) {
        final FacesContext ctx = getFacesContext();
        final FacesMessage fm =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        ctx.addMessage(getRootViewComponentId(), fm);
        
    }

    /**
     * Add JSF error message for a specific attribute.
     * @param attrName name of attribute
     * @param msg error message string
     */
    public static void addFacesErrorMessage(final String attrName,final String msg) {
        final FacesContext ctx = getFacesContext();
        final FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, attrName, msg);
        ctx.addMessage(getRootViewComponentId(), fm);
    }

    // Informational getters

    /**
     * Get view id of the view root.
     * @return view id of the view root
     */
    public static String getRootViewId() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * Get component id of the view root.
     * @return component id of the view root
     */
    public static String getRootViewComponentId() {
        return getFacesContext().getViewRoot().getId();
    }

    /**
     * Get FacesContext.
     * @return FacesContext
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    /*
* Internal method to pull out the correct local
* message bundle
*/

    private static ResourceBundle getBundle() {
        final FacesContext ctx = getFacesContext();
        final UIViewRoot uiRoot = ctx.getViewRoot();
        final Locale locale = uiRoot.getLocale();
        final ClassLoader ldr = Thread.currentThread().getContextClassLoader();
        return ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(),
                                        locale, ldr);
    }

    /**
     * Get an HTTP Request attribute.
     * @param name attribute name
     * @return attribute value
     */
    public static Object getRequestAttribute(final String name) {
        return getFacesContext().getExternalContext().getRequestMap().get(name);
    }

    /**
     * Set an HTTP Request attribute.
     * @param name attribute name
     * @param value attribute value
     */
    public static void setRequestAttribute(final String name, final Object value) {
        getFacesContext().getExternalContext().getRequestMap().put(name,
                                                                   value);
    }

    /*
* Internal method to proxy for resource keys that don't exist
*/

    private static String getStringSafely(final ResourceBundle bundle, final String key,
                                          final String defaultValue) {
        String resource = null;
        try {
            resource = bundle.getString(key);
        } catch (MissingResourceException mrex) {
            if (defaultValue != null) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        }
        return resource;
    }

    /**
     * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponentInRoot(final String id) {
        UIComponent component = null;
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            final UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }

    /**
     * Locate an UIComponent from its root component.
     * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
     * @param base root Component (parent)
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponent(final UIComponent base, final String id) {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        final Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent)childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    /**
     * Method to create a redirect URL. The assumption is that the JSF servlet mapping is
     * "faces", which is the default
     *
     * @param view the JSP or JSPX page to redirect to
     * @return a URL to redirect to
     */
    public static String getPageURL(final String view) {
        final FacesContext facesContext = getFacesContext();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final String url =
            ((HttpServletRequest)externalContext.getRequest()).getRequestURL().toString();
        final StringBuffer newUrlBuffer = new StringBuffer();
        newUrlBuffer.append(url.substring(0, url.lastIndexOf("faces/")));
        newUrlBuffer.append("faces");
        final String targetPageUrl = view.startsWith("/") ? view : "/" + view;
        newUrlBuffer.append(targetPageUrl);
        return newUrlBuffer.toString();
    }

    public static String getAppBaseURL() {
        final HttpServletRequest request =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final String contextPath = request.getContextPath();

        return contextPath;
    }
    /**
       * Returns the current HTTP session.
       */
      public static HttpSession getHttpSession() {
        final FacesContext facescontext = FacesContext.getCurrentInstance();
        final HttpSession session = (HttpSession) facescontext.getExternalContext().getSession(true);
        return session;
      }

      /**
       * Returns the current servlet response object instance.
       */
      public static HttpServletResponse getHttpServletResponse() {
        final FacesContext facescontext = FacesContext.getCurrentInstance();
        final HttpServletResponse response=(HttpServletResponse)facescontext.getExternalContext().getResponse();
        return response;
      }

      /**
       * Returns the current servlet request object instance.
       */
      public static HttpServletRequest getHttpServletRequest() {
        final FacesContext facescontext = FacesContext.getCurrentInstance();
        final HttpServletRequest request=(HttpServletRequest)facescontext.getExternalContext().getRequest();
        return request;
      }

      /**
       * Returns the servlet context.
       */
      public static ServletContext getServletContext() {
        final ServletContext context = getHttpSession().getServletContext();
        return context;
      }

      /**
       * Creates or returns the backing bean.
       *
       * @param name The name of the bean.
       * @return The backing bean.
       */
      @SuppressWarnings("deprecation")
      public static Object getBackingBean(final String name) {
        Object bean = getHttpSession().getAttribute(name);
        if (bean == null) {
          final FacesContext facescontext = FacesContext.getCurrentInstance();
          bean = facescontext.getApplication().createValueBinding("#{"+name+"}").getValue(facescontext); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return bean;
      }
      /**
       * Adds a SEVERE message.
       *
       * @param message The message.
       */
      public static void addErrorMessage(final String message) {
        final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        
        final FacesContext context = FacesContext.getCurrentInstance();
          context.addMessage(null, facesMsg);
      }
      /**
       * Adds an INFO message.
       *
       * @param message The message.
       */
      public static void addInfoMessage(final String message) {
        final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        final FacesContext context = FacesContext.getCurrentInstance();
          context.addMessage(null, facesMsg);
      }
      /**
       * Adds a WARNING message.
       *
       * @param message The message.
       */
      public static void addWarningMessage(final String message) {
        final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
        final FacesContext context = FacesContext.getCurrentInstance();
          context.addMessage(null, facesMsg);
      }

      /**
       * Removes all current messages held by the JSF framework.
       */
      @SuppressWarnings("unchecked") //$NON-NLS-1$
      public static void resetMessages() {
        final FacesContext context = FacesContext.getCurrentInstance();
          final Iterator iter = context.getMessages();
          while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
      }
    public static String handleNavigation(final String page,final String pageWithExtention) {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), page, pageWithExtention);  
        return FacesContext.getCurrentInstance().getApplication().getNavigationHandler().toString(); 
    }
      public static String redirectPage(final String pageWithExtention) {
          FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, pageWithExtention);  
          return FacesContext.getCurrentInstance().getApplication().getNavigationHandler().toString(); 
      }
      
      public static String redirectPage(final String pageWithExtention, final String params) {
          FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, pageWithExtention + "?" + params);  
          return FacesContext.getCurrentInstance().getApplication().getNavigationHandler().toString(); 
      }
    public static void refreshComponent(final UIComponent comp) {
        AdfFacesContext adfFacesContext;
        adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(comp);
    }
    public static void refreshListComponents(final String[] lista) {
        if(lista!=null && lista.length>0){
            for(int i=0;i<lista.length;i++){
                if(lista[i]!=null && !"".equals(lista[i])){
                    JSFUtil.refreshComponent(JSFUtil.findComponentInRoot(lista[i]));
                }
            }
        }
    }
    public static DCIteratorBinding getIterator(final String iteratorName) {
        final String name =
            iteratorName.endsWith("Iterator") ? iteratorName : iteratorName +
            "Iterator";
        return getBindingContainer().findIteratorBinding(name);
    }
    public static DCBindingContainer getBindingContainer() {
        return (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    }
}
