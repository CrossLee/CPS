package com.cemso.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.cemso.dto.OnebyoneDTO;
import com.cemso.dto.PlaylistDTO;
import com.cemso.dto.ProgramDTO;
import com.cemso.dto.ResourceDTO;
import com.cemso.dto.TemplateDTO;
import com.cemso.dto.TreeNodeDTO;

public class XmlOperationTool {

	public static String RESOURCEXMLPATH = "D:/CPSolution/XmlDB/ResourceDB.xml";
	public static String TEMPLATEXMLPATH = "D:/CPSolution/XmlDB/TemplateDB.xml";
	public static String PROGRAMXMLPATH = "D:/CPSolution/XmlDB/ProgramDB.xml";
	public static String PLAYLISTXMLPATH = "D:/CPSolution/XmlDB/PlaylistDB.xml";
	public static String USERXMLPATH = "D:/CPSolution/XmlDB/UserDB.xml";
	public static String DEVICEXMLPATH = "D:/CPSolution/XmlDB/DeviceDB.xml";
	public static String PLAYLIST_TEMPFILE_FOLDER = "D:/CPSolution/XmlDB/";
	public static String CONFIG_FOLDER = "C:/CPSolution/Config/";
	public static String WEB_CONTENT = "C:/apache-tomcat-6.0.24/webapps/CPS/";

	public static final String DB_TYPE_RESOURCE = "resource";
	public static final String DB_TYPE_TEMPLATE = "template";
	public static final String DB_TYPE_PROGRAM = "program";
	public static final String DB_TYPE_PLAYLIST = "playlist";
	public static final String DB_TYPE_USER = "user";
	public static final String DB_TYPE_DEVICE = "device";
	
	public static boolean isWindos = true;
	public static boolean isTest = false;

	private static final Log log = LogFactory
			.getLog(com.cemso.util.XmlOperationTool.class);

	public static void newPlaylistXML(PlaylistDTO playlist, String filePath)
			throws JDOMException, IOException {
		String xmlPath = filePath;

		// 创建节点program
		Element root = new Element("playlist");
		// 给program节点添加属性id,name
		root.setAttribute("id", playlist.getId());
		root.setAttribute("name", playlist.getName());

		Document doc = new Document(root);

		// 给program节点添加子节点并赋值
		Element param1 = new Element("param");
		param1.setAttribute("name", "remark");
		param1.setText(playlist.getRemark());

		Element param2 = new Element("param");
		param2.setAttribute("name", "createtime");
		param2.setText(playlist.getCreatetime());

		Element param3 = new Element("param");
		param3.setAttribute("name", "createby");
		param3.setText(playlist.getCreateby());

		Element param4 = new Element("param");
		param4.setAttribute("name", "startnow");
		param4.setText(String.valueOf(playlist.isStartnow()));

		Element param5 = new Element("param");
		param5.setAttribute("name", "starttime");
		param5.setText(playlist.getStarttime());

		Element param6 = new Element("param");
		param6.setAttribute("name", "endtime");
		param6.setText(playlist.getEndtime());

		Element param7 = new Element("param");
		param7.setAttribute("name", "programs");

		// 添加programs
		int size = playlist.getProgramList().size();

		for (int i = 0; i < size; i++) {
			ProgramDTO program = playlist.getProgramList().get(i);
			Element paramChild = new Element("program");
			paramChild.setAttribute("id", program.getId());
			paramChild.setAttribute("name", program.getName());
			paramChild.setAttribute("indexNum", program.getIndexNum());

			Element programCC1 = new Element("param");
			programCC1.setAttribute("name", "remark");
			programCC1.setText(program.getRemark());

			Element programCC2 = new Element("param");
			programCC2.setAttribute("name", "createtime");
			programCC2.setText(program.getCreatetime());

			Element programCC3 = new Element("param");
			programCC3.setAttribute("name", "createby");
			programCC3.setText(program.getCreateby());

			Element programCC4 = new Element("param");
			programCC4.setAttribute("name", "length");
			programCC4.setText(program.getLength());

			Element programCC5 = new Element("param");
			programCC5.setAttribute("name", "template");
			programCC5.setText(program.getTemplateId());

			Element programCC6 = new Element("param");
			programCC6.setAttribute("name", "onebyones");

			for (OnebyoneDTO onebyone : program.getOnebyoneList()) {
				Element programCCC = new Element("onebyone");
				programCCC.setAttribute("resource", onebyone.getResourceName());
				programCCC.setAttribute("dimension", onebyone.getDimension());
				programCCC.setAttribute("gradation", onebyone.getGradation());
				if(onebyone.getDirection()!=null){
					programCCC.setAttribute("direction", onebyone.getDirection());
				}
				programCC6.addContent(programCCC);
			}

			paramChild.addContent(programCC1);
			paramChild.addContent(programCC2);
			paramChild.addContent(programCC3);
			paramChild.addContent(programCC4);
			paramChild.addContent(programCC5);
			paramChild.addContent(programCC6);

			param7.addContent(paramChild);
		}

		// 给父节点添加person子节点
		root.addContent(param1);
		root.addContent(param2);
		root.addContent(param3);
		root.addContent(param4);
		root.addContent(param5);
		root.addContent(param6);
		root.addContent(param7);

		XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
		output.output(doc, new FileOutputStream(xmlPath));
	}

	// 修改数据
	public static void edit(Object object) throws JDOMException,
			FileNotFoundException, IOException {
		SAXBuilder builder = new SAXBuilder();
		String xmlPath = "";

		// resource object
		if (object instanceof ResourceDTO) {
			xmlPath = RESOURCEXMLPATH;
			ResourceDTO resource = (ResourceDTO) object;
			Document document = builder.build(xmlPath);
			Element root = document.getRootElement();

			List<?> list = root.getChildren();
			Iterator<?> it = list.iterator();
			for (int i = 0; i < list.size(); i++) {
				Element e = (Element) it.next();
				if (e.getAttributeValue("id").equals(resource.getId())) {

					List<?> paramList = e.getChildren();
					for (int j = 0; j < paramList.size(); j++) {
						Element ee = (Element) paramList.get(j);
						if (ee.getAttributeValue("name").equals("remark")) {
							ee.setText(resource.getParamRemark());
						}
						if (ee.getAttributeValue("name").equals("createtime")) {
							ee.setText(resource.getParamCreatetime());
						}
						if (ee.getAttributeValue("name").equals("createby")) {
							ee.setText(resource.getParamCreateby());
						}
					}
				}
			}
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(document, new FileOutputStream(xmlPath));
		}

		// template object
		if (object instanceof TemplateDTO) {
			xmlPath = TEMPLATEXMLPATH;
			TemplateDTO template = (TemplateDTO) object;
			Document document = builder.build(xmlPath);
			Element root = document.getRootElement();

			List<?> list = root.getChildren();
			Iterator<?> it = list.iterator();
			for (int i = 0; i < list.size(); i++) {
				Element e = (Element) it.next();
				if (e.getAttributeValue("id").equals(template.getId())) {

					List<?> paramList = e.getChildren();
					for (int j = 0; j < paramList.size(); j++) {
						Element ee = (Element) paramList.get(j);
						if (ee.getAttributeValue("name").equals("remark")) {
							ee.setText(template.getRemark());
						}
						if (ee.getAttributeValue("name").equals("createtime")) {
							ee.setText(template.getCreateTime());
						}
						if (ee.getAttributeValue("name").equals("createby")) {
							ee.setText(template.getCreateBy());
						}
					}
				}
			}
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(document, new FileOutputStream(xmlPath));
		}
	}

	// add node to tree.xml
	public static boolean addNodeToTreeXml(TreeNodeDTO node, String xmlPath) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(xmlPath);
			Element root = document.getRootElement();
			XPath xpath = XPath.newInstance("//tree[@id='" + node.getParentId()
					+ "']");
			Element e = (Element) xpath.selectSingleNode(root);

			Element nodeElement = new Element("tree");
			nodeElement.setAttribute("id", node.getId());
			nodeElement.setAttribute("text", node.getText());
			nodeElement.setAttribute("toolTip", node.getText());
			nodeElement.setAttribute("action", node.getAction());
			nodeElement.setAttribute("expanded", node.getExpanded());
			e.addContent(nodeElement);
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat().setEncoding("GBK"));
			out.output(document, new FileOutputStream(xmlPath));
			return true;
		} catch (JDOMException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			return false;
		}
	}

	// delete node from tree.xml
	public static boolean delNodeToTreeXml(TreeNodeDTO node, String xmlPath) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(xmlPath);
			Element root = document.getRootElement();
			XPath xpath = XPath.newInstance("//tree[@id='" + node.getId()
					+ "']");
			Element e = (Element) xpath.selectSingleNode(root);

			e.getParentElement().removeContent(e);

			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat().setEncoding("GBK"));
			out.output(document, new FileOutputStream(xmlPath));
			return true;
		} catch (JDOMException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			return false;
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			return false;
		}
	}

	public static String returnStringOfXml(String xmlPath) throws IOException {

		File file = new File(xmlPath);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), "GBK");

		BufferedReader in = new BufferedReader(read);
		String line;
		StringBuffer myreadline = new StringBuffer();
		while ((line = in.readLine()) != null) {
			myreadline.append(line);
		}

		read.close();
		return myreadline.toString();
	}

	/**
	 * @param xmlPath
	 * @param nodeName
	 * @param attrName
	 * @param attrValue
	 * @return boolean
	 */
	// search one node with attribute value
	public static boolean nodeIsExist(String xmlPath, String nodeName, String attrName, String attrValue){
	    boolean flag = false;
	    try {
	        SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlPath);
            Element root = document.getRootElement();
            XPath xpath = XPath.newInstance("//" + nodeName + "[@" + attrName + "='" + attrValue + "']");
            Element e = (Element) xpath.selectSingleNode(root);
            if(e != null){
                flag = true;
            }
        } catch (JDOMException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> nodeIsExist throw an exception : JDOMException");
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> nodeIsExist throw an exception : IOException");
                log.error(e.getMessage());
            }
        }
	    return flag;
	}
	
    /**
     * @param xmlPath xml file path
     * @param nodeName node name
     * @param attrName attribute name
     * @param attrValue attribute value
     * @param nodeText node text
     * @return boolean
     */
	// search one node with node text
    @SuppressWarnings("unchecked")
    public static boolean nodeIsExist(String xmlPath, String nodeName, String attrName, String attrValue, String nodeText){
        boolean flag = false;
        
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlPath);
            Element root = document.getRootElement();
            XPath xpath = XPath.newInstance("//" + nodeName + "[@" + attrName + "='" + attrValue + "']");
            List<Element> elist = (List<Element>) xpath.selectNodes(root);
            
            for(Element e : elist){
                if(e.getText().equals(nodeText)){
                    flag = true;
                    break;
                }
            }
        } catch (JDOMException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> nodeIsExist throw an exception : JDOMException");
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> nodeIsExist throw an exception : IOException");
                log.error(e.getMessage());
            }
        }
        return flag;
    }
    
    public static String getNodeIdValue(String xmlPath, String nodeName, String id, String attrName){
        String value = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlPath);
            Element root = document.getRootElement();
            XPath xpath = XPath.newInstance("//" + nodeName + "[@id='" + id + "']");
            Element e = (Element) xpath.selectSingleNode(root);
            if(e != null){
            	value =  e.getAttributeValue(attrName);
            }
        } catch (JDOMException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : JDOMException");
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : IOException");
                log.error(e.getMessage());
            }
        }
        return value;
    }
    
    /**
     * @param xmlPath xml file path
     * @param nodeName node name
     * @param paramAttrName the attribute name of param node
     * @return the text of param node
     */
    @SuppressWarnings("unchecked")
    public static String getElementParamText(String xmlPath, String nodeName, String name, String paramAttrName){
        String value = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlPath);
            Element root = document.getRootElement();
            XPath xpath = XPath.newInstance("//" + nodeName + "[@name='" + name + "']");
            Element e = (Element) xpath.selectSingleNode(root);
            if(e != null){
                List<Element> children = e.getChildren();
                for(Element ee : children){
                    if(ee.getAttributeValue("name").equals(paramAttrName)){
                        value = ee.getText();
                    }
                }
            }
        } catch (JDOMException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : JDOMException");
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : IOException");
                log.error(e.getMessage());
            }
        }
        return value;
    }
    
    /**
     * @param xmlPath xml file path
     * @param nodeName node name
     * @param attrName attribute name
     * @param attrValue attribute value
     * @param paramAttrName the attribute name of param node
     * @return the text of param node
     */
    @SuppressWarnings("unchecked")
    public static String getElementParamValue(String xmlPath, String nodeName, String attrName, String attrValue, String paramAttrName){
        String value = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(xmlPath);
            Element root = document.getRootElement();
            XPath xpath = XPath.newInstance("//" + nodeName + "[@" + attrName + "='" + attrValue + "']");
            Element e = (Element) xpath.selectSingleNode(root);
            if(e != null){
                List<Element> children = e.getChildren();
                for(Element ee : children){
                    if(ee.getAttributeValue("name").equals(paramAttrName)){
                        value = ee.getText();
                    }
                }
            }
        } catch (JDOMException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : JDOMException");
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if(log.isErrorEnabled()){
                log.error("XmlOperationTool --> getNodeIdValue throw an exception : IOException");
                log.error(e.getMessage());
            }
        }
        return value;
    }
}
