package com.soaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.binding.ControlBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import oracle.adf.model.events.EventDispatcher;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/**
 * A series of convenience functions for dealing with ADF Bindings.
 * Note: Updated for JDeveloper 11
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 * $Id: ADFUtil.java 2513 2007-09-20 20:39:13Z ralsmith $.
 */
public class ADFUtil {

    public static final ADFLogger LOGGER =
        ADFLogger.createADFLogger(ADFUtil.class);

    /**
     * Get application module for an application module data control by name.
     * @param name application module data control name
     * @return ApplicationModule
     */
    public static ApplicationModule getApplicationModuleForDataControl(final String name) {

        return (ApplicationModule)JSFUtil.resolveExpression("#{data." + name +
                                                            ".dataProvider}");
    }

    public static Map<String, Object> getPageFlowScope() {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope();
    }

    /**
     * A convenience method for getting the value of a bound attribute in the
     * current page context programatically.
     * @param attributeName of the bound value in the pageDef
     * @return value of the attribute
     */
    public static Object getBoundAttributeValue(final String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }

    /**
     * A convenience method for setting the value of a bound attribute in the
     * context of the current page.
     * @param attributeName of the bound value in the pageDef
     * @param value to set
     */
    public static void setBoundAttributeValue(final String attributeName,
                                              final Object value) {
        findControlBinding(attributeName).setInputValue(value);
    }

    /**
     * Returns the evaluated value of a pageDef parameter.
     * @param pageDefName reference to the page definition file of the page with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */
    public static Object getPageDefParameterValue(final String pageDefName,
                                                  final String parameterName) {
        final BindingContainer bindings = findBindingContainer(pageDefName);
        final DCParameter param =
            ((DCBindingContainer)bindings).findParameter(parameterName);
        return param.getValue();
    }

    /**
     * Convenience method to find a DCControlBinding as an AttributeBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param bindingContainer binding container
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(final BindingContainer bindingContainer,
                                                      final String attributeName) {
        if (attributeName != null && bindingContainer != null) {
            final ControlBinding ctrlBinding =
                bindingContainer.getControlBinding(attributeName);
            if (ctrlBinding instanceof AttributeBinding) {
                return (AttributeBinding)ctrlBinding;
            }

        }
        return null;
    }

    /**
     * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */
    public static AttributeBinding findControlBinding(final String attributeName) {
        return findControlBinding(getBindingContainer(), attributeName);
    }

    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer)JSFUtil.resolveExpression("#{bindings}");
    }

    /**
     * Return the Binding Container as a DCBindingContainer.
     * @return current binding container as a DCBindingContainer
     */
    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindingContainer();
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List selectItemsForIterator(final String iteratorName,
                                              final String valueAttrName,
                                              final String displayAttrName) {
        return selectItemsForIterator(findIterator(iteratorName),
                                      valueAttrName, displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute to use for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List selectItemsForIterator(final String iteratorName,
                                              final String valueAttrName,
                                              final String displayAttrName,
                                              final String descriptionAttrName) {
        return selectItemsForIterator(findIterator(iteratorName),
                                      valueAttrName, displayAttrName,
                                      descriptionAttrName);
    }

    /**
     * Get List of attribute values for an iterator.
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName value attribute to use
     * @return List of attribute values for an iterator
     */
    public static List attributeListForIterator(final String iteratorName,
                                                final String valueAttrName) {
        return attributeListForIterator(findIterator(iteratorName),
                                        valueAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iteratorName iterabot binding name
     * @return List of Key objects for rows
     */
    public static List keyListForIterator(final String iteratorName) {
        return keyListForIterator(findIterator(iteratorName));
    }

    /**
     * Get List of Key objects for rows in an iterator.
     * @param iter iterator binding
     * @return List of Key objects for rows
     */
    public static List keyListForIterator(final DCIteratorBinding iter) {
        final List attributeList = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getKey());
        }
        return attributeList;
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * @param iteratorName iterator binding name
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List keyAttrListForIterator(final String iteratorName,
                                              final String keyAttrName) {
        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);
    }

    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     *
     * @param iter iterator binding
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List keyAttrListForIterator(final DCIteratorBinding iter,
                                              final String keyAttrName) {
        final List attributeList = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
        }
        return attributeList;
    }

    /**
     * Get a List of attribute values for an iterator.
     *
     * @param iter iterator binding
     * @param valueAttrName name of value attribute to use
     * @return List of attribute values
     */
    public static List attributeListForIterator(final DCIteratorBinding iter,
                                                final String valueAttrName) {
        final List attributeList = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    /**
     * Find an iterator binding in the current binding container by name.
     *
     * @param name iterator binding name
     * @return iterator binding
     */
    public static DCIteratorBinding findIterator(final String name) {
        final DCIteratorBinding iter =
            getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");
        }
        return iter;
    }

    public static DCIteratorBinding findIterator(final String bindingContainer,
                                                 final String iterator) {
        final DCBindingContainer bindings =
            (DCBindingContainer)JSFUtil.resolveExpression("#{" +
                                                          bindingContainer +
                                                          "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" +
                                       bindingContainer + "' not found");
        }
        final DCIteratorBinding iter = bindings.findIteratorBinding(iterator);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + iterator +
                                       "' not found");
        }
        return iter;
    }

    public static JUCtrlValueBinding findCtrlBinding(final String name) {
        final JUCtrlValueBinding rowBinding =
            (JUCtrlValueBinding)getDCBindingContainer().findCtrlBinding(name);
        if (rowBinding == null) {
            throw new RuntimeException("CtrlBinding " + name + "' not found");
        }
        return rowBinding;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param name operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(final String name) {
        final OperationBinding op =
            getDCBindingContainer().getOperationBinding(name);
        if (op == null) {
            throw new RuntimeException("Operation '" + name + "' not found");
        }
        return op;
    }

    /**
     * Find an operation binding in the current binding container by name.
     *
     * @param bindingContianer binding container name
     * @param opName operation binding name
     * @return operation binding
     */
    public static OperationBinding findOperation(final String bindingContianer,
                                                 final String opName) {
        final DCBindingContainer bindings =
            (DCBindingContainer)JSFUtil.resolveExpression("#{" +
                                                          bindingContianer +
                                                          "}");
        if (bindings == null) {
            throw new RuntimeException("Binding container '" +
                                       bindingContianer + "' not found");
        }
        final OperationBinding op = bindings.getOperationBinding(opName);
        if (op == null) {
            throw new RuntimeException("Operation '" + opName + "' not found");
        }
        return op;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */
    public static List selectItemsForIterator(final DCIteratorBinding iter,
                                              final String valueAttrName,
                                              final String displayAttrName,
                                              final String descriptionAttrName) {
        final List selectItems = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),
                                           (String)r.getAttribute(displayAttrName),
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List selectItemsForIterator(final DCIteratorBinding iter,
                                              final String valueAttrName,
                                              final String displayAttrName) {
        final List selectItems = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List selectItemsByKeyForIterator(final String iteratorName,
                                                   final String displayAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName),
                                           displayAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List selectItemsByKeyForIterator(final String iteratorName,
                                                   final String displayAttrName,
                                                   final String descriptionAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName),
                                           displayAttrName,
                                           descriptionAttrName);
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List selectItemsByKeyForIterator(final DCIteratorBinding iter,
                                                   final String displayAttrName,
                                                   final String descriptionAttrName) {
        final List selectItems = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(),
                                           (String)r.getAttribute(displayAttrName),
                                           (String)r.getAttribute(descriptionAttrName)));
        }
        return selectItems;
    }

    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return List of ADF Faces SelectItem for an iterator binding
     */
    public static List selectItemsByKeyForIterator(final DCIteratorBinding iter,
                                                   final String displayAttrName) {
        final List selectItems = new ArrayList();
        for (final Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(),
                                           (String)r.getAttribute(displayAttrName)));
        }
        return selectItems;
    }

    /**
     * Find the BindingContainer for a page definition by name.
     *
     * Typically used to refer eagerly to page definition parameters. It is
     * not best practice to reference or set bindings in binding containers
     * that are not the one for the current page.
     *
     * @param pageDefName name of the page defintion XML file to use
     * @return BindingContainer ref for the named definition
     */
    private static BindingContainer findBindingContainer(final String pageDefName) {
        final BindingContext bctx = getDCBindingContainer().getBindingContext();
        final BindingContainer foundContainer =
            bctx.findBindingContainer(pageDefName);
        return foundContainer;
    }

    public static void printOperationBindingExceptions(final List opList) {
        if (opList != null && !opList.isEmpty()) {
            for (final Object error : opList) {
                LOGGER.severe(error.toString());
            }
        }
    }

    public static Object resolveExpression(final String expression) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final Application app = facesContext.getApplication();
        final ExpressionFactory elFactory = app.getExpressionFactory();
        final ELContext elContext = facesContext.getELContext();
        final ValueExpression valueExp =
            elFactory.createValueExpression(elContext, expression,
                                            Object.class);
        return valueExp.getValue(elContext);
    }

    public static void showPopup(final String popId) {
        final String js =
            "var popup;popup = AdfPage.PAGE.findComponent('" + popId + "');popup.show()";
        addScript(js);
    }

    public static void showDialog(final RichPopup popId) {
        final String js =
            "var popup;popup = AdfPage.PAGE.findComponent('" + popId.getClientId(FacesContext.getCurrentInstance()) +
            "');popup.show()";
        addScript(js);
    }

    public static void closeDialog(final RichPopup popupId) {
        final String hidePopup =
            "var popupObj=AdfPage.PAGE.findComponent('" + popupId.getClientId(FacesContext.getCurrentInstance()) +
            "'); popupObj.hide();";
        addScript(hidePopup);
    }

    public static void closePopup(final String popupId) {
        final String js = "AdfPage.PAGE.findComponent('" + popupId + "').hide();";
        addScript(js);
    }

    public static void addScript(final String script) {
        final FacesContext fc = FacesContext.getCurrentInstance();
        final ExtendedRenderKitService erks =
            Service.getRenderKitService(fc, ExtendedRenderKitService.class);
        erks.addScript(fc, script);
    }

    public static Object invokeEL(final String el, final Class[] paramTypes,
                                  final Object[] params) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final ExpressionFactory expressionFactory =
            facesContext.getApplication().getExpressionFactory();
        final MethodExpression exp =
            expressionFactory.createMethodExpression(elContext, el,
                                                     Object.class, paramTypes);
        return exp.invoke(elContext, params);
    }

    public static Object evaluateEL(final String el) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final ExpressionFactory expressionFactory =
            facesContext.getApplication().getExpressionFactory();
        final ValueExpression exp =
            expressionFactory.createValueExpression(elContext, el,
                                                    Object.class);

        return exp.getValue(elContext);
    }
    public static String getSelectItemDescription(final List<SelectItem> lista,final String cod) {
        if(lista.size()<=0){
            return "";
        }
        final Iterator<SelectItem> it=lista.iterator();
        while(it.hasNext()){
            final SelectItem item=it.next();
            if(item.getValue().equals(cod)){
                return item.getLabel();
            }
        }
        return "";
    }
    public static void dispararContextualEvent(final String nombre,final Object payload){
        final JUEventBinding eventBinding = (JUEventBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get(nombre);
        final EventDispatcher eventDispacher = ((DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry()).getEventDispatcher();
        eventDispacher.queueEvent(eventBinding, payload);
    }
}
