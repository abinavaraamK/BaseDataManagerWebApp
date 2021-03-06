package com.hex.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.hex.vo.TableVO;

public class HexPojoServiceGenerator {
	
	 private String lsPackageDir;
	 private String lsPackage;
	    
	    public void generateServiceFiles(ArrayList list) {
	        System.out.println("**********Enter into HexPojoGenerator *********");
	        //String outDirectory = "D:/Out";
	        String outDirectory = "";
	        for (int i = 0; i < list.size(); i++) {
	            HashMap poMap = (HashMap) list.get(i);
	            outDirectory = (String) poMap.get("DIRECTORY");
	            ArrayList tableList = (ArrayList) poMap.get("LIST");
	            lsPackage = (String) poMap.get("PACKAGE");
	            lsPackageDir = (String) poMap.get("PACKAGE_DIR");
	            generateVO(tableList, outDirectory);
	            generateService(tableList, outDirectory);
	            generateRestService(tableList, outDirectory);
	            //generateBusinessDeletegate(tableList, outDirectory);
	          
	        }
	        generateException(outDirectory);
	    }

	    private void generateRestService(ArrayList tableList, String outDirectory) {
			 TableVO tableVO = null;
		        String className = "";

		        tableVO = (TableVO) tableList.get(0);
		        className = HexUtil.initCap(tableVO.getTableName());
		        String lsSelectColumnsMethods = getSelectColumnsPOMethod(tableList, className);
		        
		        String poContent = getServiceContent(className, className, lsSelectColumnsMethods);
		        
		        outDirectory = outDirectory + "\\src\\" + lsPackageDir + "\\rest";
		        HexUtil.makeDirectory(outDirectory);
		        
		        String outputFile = outDirectory + "\\" + "RestService.java";
		        HexUtil.writeFile(poContent, outputFile);
			}
		
	    private  void generateService(ArrayList tableList, String outDirectory) {
	        TableVO tableVO = null;
	        String className = "";

	        tableVO = (TableVO) tableList.get(0);
	        className = HexUtil.initCap(tableVO.getTableName());
	        String lsSelectColumnsMethods = getSelectColumnsPOMethod(tableList, className);
	        
	        String poContent = getServiceContent(className, className, lsSelectColumnsMethods);
	        
	        outDirectory = outDirectory + "\\src\\" + lsPackageDir + "\\service";
	        HexUtil.makeDirectory(outDirectory);
	  
	        poContent = getServiceImplContent(className, className, lsSelectColumnsMethods);
	        String outputFile = outDirectory + "\\"+className+"Impl" + ".java";
	        HexUtil.writeFile(poContent, outputFile);
	        
	        lsSelectColumnsMethods = getSelectColumnsInterfaceMethod(tableList, className);
	        poContent = getInterfaceContent(className, className, lsSelectColumnsMethods);
	        outputFile = outDirectory + "\\I" + className + ".java"; 
	        HexUtil.writeFile(poContent, outputFile);
	    }

	    private String getServiceContent(String className, String valueObject,
	            String psSelectColumnsMethods) {
	        StringBuffer result = new StringBuffer();
	        try {
	            /*DataInputStream dis = new DataInputStream(new FileInputStream(
	                    "D:\\BaseDataManager/templates/AngularJs1/pojo\\rest\\RestService.template"));
	            */DataInputStream dis = new DataInputStream(new FileInputStream(
	            "templates\\AngularJs1\\pojo\\rest\\RestService.template"));
	   
	            while (dis.available() > 0) {
	                String line = dis.readLine();

	                line = HexUtil.replaceTags(line, "<ClassName>", className);
	                line = HexUtil.replaceTags(line, "<LowerClassName>",
	                        className.toLowerCase());
	                line = HexUtil.replaceTags(line, "<ValueObject>", valueObject);
	                line = HexUtil.replaceTags(line, "<packageName>",
	                        HexUtil.initSmall(className));
	                line = HexUtil.replaceTags(line, "<SelectColumnsMethods>",
	                        psSelectColumnsMethods);
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);
	                result.append(line);
	                result.append("\n");
	            }
	            dis.close();
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return result.toString();
	    }

	    private String getServiceImplContent(String className, String valueObject,
	            String psSelectColumnsMethods) {
	        StringBuffer result = new StringBuffer();
	        try {
	          /*  DataInputStream dis = new DataInputStream(new FileInputStream(
	                    "D:\\BaseDataManager/templates/AngularJs1/pojo\\service\\RestService.template"));
	           */ DataInputStream dis = new DataInputStream(new FileInputStream(
                "templates\\AngularJs1\\pojo\\service\\ServiceImpl.template"));
       
	            while (dis.available() > 0) {
	                String line = dis.readLine();

	                line = HexUtil.replaceTags(line, "<ClassName>", className);
	                line = HexUtil.replaceTags(line, "<LowerClassName>",
	                        className.toLowerCase());
	                line = HexUtil.replaceTags(line, "<ValueObject>", valueObject);
	                line = HexUtil.replaceTags(line, "<packageName>",
	                        HexUtil.initSmall(className));
	                line = HexUtil.replaceTags(line, "<SelectColumnsMethods>",
	                        psSelectColumnsMethods);
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);
	                result.append(line);
	                result.append("\n");
	            }
	            dis.close();
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return result.toString();
	    }

	    private String getInterfaceContent(String className, String valueObject,
	            String psSelectColumnsMethods) {
	        StringBuffer result = new StringBuffer();
	        try {
	    /*    	 DataInputStream dis = new DataInputStream(new FileInputStream(
                 "D:\\BaseDataManager/templates/AngularJs1/pojo\\service\\ServiceInterface.template"));
        */
	            DataInputStream dis = new DataInputStream(new FileInputStream(
	                    "templates\\AngularJs1\\pojo\\service\\ServiceInterface.template"));
	            while (dis.available() > 0) {
	                String line = dis.readLine();

	                line = HexUtil.replaceTags(line, "<ClassName>", className);
	                line = HexUtil.replaceTags(line, "<LowerClassName>",
	                        className.toLowerCase());
	                line = HexUtil.replaceTags(line, "<ValueObject>", valueObject);
	                line = HexUtil.replaceTags(line, "<packageName>",
	                        HexUtil.initSmall(className));
	                line = HexUtil.replaceTags(line, "<SelectColumnsMethods>",
	                        psSelectColumnsMethods);
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);
	                result.append(line);
	                result.append("\n");
	            }
	            dis.close();
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return result.toString();
	    }
	 
	    public void generateAngularJs1Files(ArrayList columns, String psObjectName,
	            String psDistinationDir, String psPackage,
	            String psPackageDir,
	            String psPageTitle) {
	        lsPackageDir = psPackageDir;
	        lsPackage = psPackage;
	    }

	    private void generateVO(ArrayList tableList, String outDirectory)  {

	        TableVO tableVO = null;
	        String lsVariables = "";
	        String lsMethods = "";
	        String property = "";
	        String dataType = "";
	        String className = "";
	        String[] setter = null;

	        for (int count = 0; count < tableList.size(); count++) {

	            tableVO = (TableVO) tableList.get(count);
	            property = tableVO.getColumnName().toLowerCase();
	            dataType = HexUtil.getDataType(tableVO);
	            String controlType = tableVO.getControlType();
	            DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	            Date today = Calendar.getInstance().getTime();
	            
	            java.sql.Date sqlDate = new java.sql.Date(today .getTime());
	            
	            
	           if ((controlType.equalsIgnoreCase(HexFrameConstants.DATE_BOX1)) || (controlType.equalsIgnoreCase(HexFrameConstants.DATE_BOX2)))
	            {
	        	   lsVariables = lsVariables + "\t" + "private " + dataType + " " + property +"=null"+ 
	               ";\n";
	            
	            }
	           else if ((controlType.equalsIgnoreCase(HexFrameConstants.TEXT_BOX1)) || (controlType.equalsIgnoreCase(HexFrameConstants.TEXT_BOX2)))
	            {
	            	
						
							lsVariables = lsVariables + "\t" + "private " + dataType + " " + property + "=\"" + ""+ "\"" +
							        ";\n";
						
				
	            }
	            
	            
	            else
	            { 
	            lsVariables = lsVariables + "\t" + "private " + dataType + " " + property +
	                    ";\n";
	            }
	            
	            
	            
	            
	            lsMethods = lsMethods + "\t" + "public void set" +
	                    HexUtil.initCap(property) +
	                    "(" + dataType + " newValue) { \n";

	            lsMethods = lsMethods + "\t" + "\t" + property + " = newValue; \n";
	            lsMethods = lsMethods + "\t" + "}\n\n";
	            lsMethods = lsMethods + "\t" + "public " + dataType + " get" +
	                    HexUtil.initCap(property) +
	                    "() { \n";
	            lsMethods = lsMethods + "\t" + "\t" + "return " + property + ";\n ";
	            lsMethods = lsMethods + "\t" + "}\n\n";
	        }
	        className = HexUtil.initCap(tableVO.getTableName());
	        String valueObject = getValueObject(lsVariables, lsMethods,
	                className);
	        outDirectory = outDirectory + "\\src\\" + lsPackageDir + "\\vo";

	        HexUtil.makeDirectory(outDirectory);

	        String outputFile = outDirectory + "\\" + className + ".java";
	        HexUtil.writeFile(valueObject, outputFile);
	    }
	    private String getValueObject(String psVariables, String psMethods,
	            String object) {
	        StringBuffer result = new StringBuffer();
	        object = HexUtil.initCap(object);
	        try {
	            DataInputStream dis = new DataInputStream(new FileInputStream(
	                    /*D:\\BaseDataManager\\*/"templates\\vo\\VO.template"));
	            while (dis.available() > 0) {
	                String line = dis.readLine();
	                line = HexUtil.replaceTags(line, "<ClassName>", object);
	                line = HexUtil.replaceTags(line, "<PrivateVariables>", psVariables);
	                line = HexUtil.replaceTags(line, "<SetGetMethods>", psMethods);
	                line = HexUtil.replaceTags(line, "<packageName>", HexUtil.initSmall(object));
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);

	                result.append(line);
	                result.append("\n");
	            }
	            dis.close();

	        } catch (Exception exception) {

	            exception.printStackTrace();
	        }

	        return result.toString();
	    }

	    private String getSelectColumnsPOMethod(ArrayList poTableList, String psClassName) {
	        //psClassName = psClassName + "Dao";
	        StringBuffer buffer = new StringBuffer();
	        for (int index = 0; index < poTableList.size(); index++) {
	            TableVO tableVO = (TableVO) poTableList.get(index);
	            String lsTargetTable = tableVO.getTargetTable();
	            if (lsTargetTable != null && lsTargetTable.length() > 0) {
	                lsTargetTable = lsTargetTable.toLowerCase();
	                String lsKeyColumn = tableVO.getKeyColumn().toLowerCase();
	                String lsValueColumn = tableVO.getValueColumn().toLowerCase();
	                String lsMethodName = "getSelect" + HexUtil.initCap(lsTargetTable) +
	                        HexUtil.initCap(lsKeyColumn) +
	                        HexUtil.initCap(lsValueColumn);
	                buffer.append("\tpublic java.util.List " + lsMethodName + "() throws HexApplicationException {\n");
	                buffer.append("\t\tSystem.out.println(\"inside " + lsMethodName +
	                        " in Service\");\n");
	                //buffer.append("\t\t" +psClassName + " dao = (" + psClassName + ") BootStrapper.getService().getBean(\"" + psClassName + "\");\n");
	                buffer.append("\t\treturn " + psClassName.toLowerCase() + "Dao." +
	                        lsMethodName + "();\n");
	                buffer.append("\t}\n\n");
	            }
	        }
	        return buffer.toString();
	    }

	    private String getSelectColumnsInterfaceMethod(ArrayList poTableList, String psClassName) {
	        StringBuffer buffer = new StringBuffer();
	        for (int index = 0; index < poTableList.size(); index++) {
	            TableVO tableVO = (TableVO) poTableList.get(index);
	            String lsTargetTable = tableVO.getTargetTable();
	            if (lsTargetTable != null && lsTargetTable.length() > 0) {
	                lsTargetTable = lsTargetTable.toLowerCase();
	                String lsKeyColumn = tableVO.getKeyColumn().toLowerCase();
	                String lsValueColumn = tableVO.getValueColumn().toLowerCase();
	                String lsMethodName = "getSelect" + HexUtil.initCap(lsTargetTable) +
	                        HexUtil.initCap(lsKeyColumn) +
	                        HexUtil.initCap(lsValueColumn);
	                buffer.append("\tpublic java.util.List " + lsMethodName + "() throws HexApplicationException; \n");
	                //buffer.append("\t\tSystem.out.println(\"inside " + lsMethodName + " in PO\");\n");
	                //buffer.append("\t\t" +psClassName + " dao = (" + psClassName + ") BootStrapper.getService().getBean(\"" + psClassName + "\");\n");
	                //buffer.append("\t\treturn " + psClassName.toLowerCase() + "Dao." + lsMethodName + "();\n");
	                buffer.append("\n\n");
	            }
	        }
	        return buffer.toString();
	    }

	    private void generateException(String outDirectory) {

	        //System.out.println(" Inside generateBootStrapper ");
	        StringBuffer buffer = new StringBuffer();
	        try {
	            DataInputStream dis = new DataInputStream(new FileInputStream(
	                    /*D:\\BaseDataManager\\*/"templates\\exception\\HexApplicationException.template"));
	            while (dis.available() > 0) {
	                String line = dis.readLine();
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);
	                buffer.append(line);
	                buffer.append("\n");
	            }
	            dis.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        outDirectory = outDirectory + "\\src\\" + lsPackageDir + "\\util";
	        HexUtil.makeDirectory(outDirectory);
	        String outputFile = outDirectory + "\\" + "HexApplicationException.java";
	        HexUtil.writeFile(buffer.toString(), outputFile);
	    }

	   /* private void generateBusinessDeletegate(ArrayList tableList, String outDirectory) {
	        TableVO tableVO = null;
	        String className = "";
	        tableVO = (TableVO) tableList.get(0);
	        className = HexUtil.initCap(tableVO.getTableName());
	        String lsSelectColumnsMethods = getSelectColumnsDelegateMethod(tableList, className);
	        String poContent = getDelegateObject(className, className, lsSelectColumnsMethods);
	        outDirectory = outDirectory + "\\src\\" + lsPackageDir + "\\delegate";
	        HexUtil.makeDirectory(outDirectory);
	        String outputFile = outDirectory + "\\" + className + "BusinessDelegate.java";
	        HexUtil.writeFile(poContent, outputFile);
	    }*/

	    private String getSelectColumnsDelegateMethod(ArrayList poTableList, String psClassName) {
	        //psClassName = psClassName + "DAO";
	        StringBuffer buffer = new StringBuffer();
	        for (int index = 0; index < poTableList.size(); index++) {
	            TableVO tableVO = (TableVO) poTableList.get(index);
	            String lsTargetTable = tableVO.getTargetTable();
	            if (lsTargetTable != null && lsTargetTable.length() > 0) {
	                lsTargetTable = lsTargetTable.toLowerCase();
	                String lsKeyColumn = tableVO.getKeyColumn().toLowerCase();
	                String lsValueColumn = tableVO.getValueColumn().toLowerCase();
	                String lsMethodName = "getSelect" + HexUtil.initCap(lsTargetTable) +
	                        HexUtil.initCap(lsKeyColumn) +
	                        HexUtil.initCap(lsValueColumn);
	                buffer.append("\tpublic java.util.List " + lsMethodName + "() throws HexApplicationException {\n");
	                buffer.append("\t\tSystem.out.println(\"inside " + lsMethodName +
	                        " in BusinessDelegate\");\n");
	                buffer.append("\t\treturn " + psClassName.toLowerCase() + "." +
	                        lsMethodName + "();\n");
	                buffer.append("\t}\n\n");
	            }
	        }
	        return buffer.toString();

	    }

	    private String getDelegateObject(String className, String valueObject,
	            String psSelectColumnsMethods) {
	        StringBuffer result = new StringBuffer();
	        try {
	            DataInputStream dis = new DataInputStream(new FileInputStream(
	                    /*D:\\BaseDataManager\\*/"templates\\AngularJs1\\businessdelegate\\BusinessDelegate.template"));
	            while (dis.available() > 0) {
	                String line = dis.readLine();
	                line = HexUtil.replaceTags(line, "<ClassName>", className);
	                line = HexUtil.replaceTags(line, "<LowerClassName>",
	                        className.toLowerCase());
	                line = HexUtil.replaceTags(line, "<ValueObject>", valueObject);
	                line = HexUtil.replaceTags(line, "<Package>", lsPackage);
	                line = HexUtil.replaceTags(line, "<SelectColumnsMethods>",
	                        psSelectColumnsMethods);
	                //line = HexUtil.replaceTags(line,"<ProcessClassName>",className+"PO");
	                result.append(line);
	                result.append("\n");
	            }
	            dis.close();
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return result.toString();
	    }

	    
}
