<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Main Page</title>
<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="resources/css/bdm_app.css" var="coreCss" />
<spring:url value="resources/js/jquery-2.1.0.min.js" var="Jquery"/>
<spring:url value="resources/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="resources/js/bdm_app.js" var="bdmApp" />


<link rel="stylesheet" href="${bootstrapCss}">
<link rel="stylesheet" href="${coreCss}">
<script src="${Jquery}" type="text/javascript"></script>
<script src="${bootstrapJs}" type="text/javascript"></script>
<script src="${bdmApp}"></script>
<script>


$(document).ready(function() {


	 $("#save").on("click", function() {
			
			var index = $('#index').val();
		
			$('input[name^="listTableVO['+index+'].labelName"]').val($('#label').val());
			$('input[name^="listTableVO['+index+'].dataLength"]').val($('#length').val());
			$('input[name^="listTableVO['+index+'].dataType"]').val($('#dataType').val());
			$('input[name^="listTableVO['+index+'].mandatory"]').val($('#readonly').val());
			$('input[name^="listTableVO['+index+'].readonly"]').val($('#readonly').val());
			
			
			$("#"+index).empty();
				$("#"+index).append($('<li>').text($('#label').val()).val(index));
			
			return false;
		});
		
		
		 $("#tableName").on("change", function() {
			
			$('#columnNames').empty();
			$('#displayNames').empty();
			$('#values').empty();
			
						
			var tableName = $('#tableName').val();	
			alert(tableName)
			var data = 'tableName='
					+ encodeURIComponent(tableName);
			alert(data)
			$.ajax({
				url : "/getTableData",
				data : data,
				type : "GET",
			
			success : function(response) {
				 $.each(response, function(index, value) {
				
				$('#values')
				.append($('<input type="hidden" id="labelName" name="listTableVO['+index+'].labelName" value='+value.columnName+'/>'))
				.append($('<input type="hidden" id="columnName" name="listTableVO['+index+'].columnName" value='+value.columnName+'/>'))
				.append($('	<input type="hidden" id="lengthhidden" name="listTableVO['+index+'].dataLength" value='+value.dataLength+' />'))
				.append($('<input type="hidden" id="readonlyhidden" name="listTableVO['+index+'].readonly" value='+value.readonly+' />'))
				.append($('<input type="hidden" id="visiblehidden" name="listTableVO['+index+'].visible" value='+value.visible+' />'))
				.append($('<input type="hidden" id="mandatoryhidden" name="listTableVO['+index+'].mandatory" value='+value.mandatory+' />'))
				.append($('<input type="hidden" id="dataTypehidden" name="listTableVO['+index+'].dataType" value='+value.dataType+' />'))
				.append($('<input type="hidden" id="tableNamehidden" name="listTableVO['+index+'].tableName" value='+value.tableName+' />'))
				.append($('<input type="hidden" id="targetTablehidden" name="listTableVO['+index+'].targetTable" value='+value.tableName+' />'))
				.append($('<input type="hidden" id="validationTypehidden" name="listTableVO['+index+'].validationType" value='+value.validationType+' />'))
				.append($('<input type="hidden" id="dataPrecisionhidden" name="listTableVO['+index+'].dateFormat" value='+value.dateFormat+' />'))
				.append($('<input type="hidden" id="visiblehidden" name="listTableVO['+index+'].dataPrecision" value='+value.dataPrecision+' />'))
				.append($('<input type="hidden" id="dataScalehidden" name="listTableVO['+index+'].dataScale" value='+value.dataScale+' />'))
				.append($('<input type="hidden" id="nullablehidden" name="listTableVO['+index+'].nullable" value='+value.nullable+' />'));
				
				
				$('#columnNames').append($('<li>').text(value.columnName).val(index));
				
				$('#displayNames').append($('<div id='+index+'>').append($('<li>').text(value.columnName).val(index)).click(function(){
						$('#length').val($('input[name^="listTableVO['+index+'].dataLength"]').val()); 	
						$('#label').val($('input[name^="listTableVO['+index+'].labelName"]').val());
						$('#dataType').val($('input[name^="listTableVO['+index+'].dataType"]').val());
						$('#readonly').prop($('input[name^="listTableVO['+index+'].readonly"]').val());
						$('#visible').prop($('input[name^="listTableVO['+index+'].visible"]').val());
						$('#mandatory').prop($('input[name^="listTableVO['+index+'].mandatory"]').val());
						$('#index').val(index);
						
						
						
						
   }));
    });
				
				var json = ${'response'};
				$('#PageLabel').val(json[0].tableName);
			
				},
				error : function(xhr, status, error) {
					alert(xhr.responseText);
				}
			});
			return false;
		});
	});

</script>
</head>
<form:form action="/generate"    modelAttribute="tableVoList" method="post" >
<body class="row" style="background: #2196F3;">
	<div class="col-md-offset-1 col-md-11" id="mainDiv"> 
		<div class="col-md-6 borderMod" id="leftDiv"> 
			<div class="col-md-10" id="table">
				<span class="col-md-6">select the table</span>
					
					<form:select class="col-md-5" id="tableName" path="tableName">
					<form:option value="0">Select </form:option>
						<form:options items="${tableList.listTableVO}" itemLabel="tableName" itemValue="tableName"/>
				</form:select> 
		
		 	</div>
		 	<div class="col-md-10" id="page">
				<span class="col-md-6">Page Title</span>
				<input type="hidden" id="tableNames" name="tableName" />
				<form:input id="PageLabel" path="labelName" class="col-md-5"/>
		 	</div>
		 	<div class="col-md-5" >
		 		<span class="col-md-10">DB Columns</span>
		 		<ul id="columnNames" class="ulDb"> 
		 		
		 		</ul>
		 	</div>
			<div id="values">
		 		
		 	</div>
			
		 	<div class="col-md-5" style="cursor:pointer">
		 		<span class="col-md-10">Display Names</span>
		 		<ul id="displayNames" class="ulDb">
		 			
		 		</ul>
		 	</div>
		 	<div class="col-md-10" id="package">
				<span class="col-md-6">Package</span>
				<form:input id="packageName" path="packageName" class="col-md-5"/>
		 	</div>
 		</div>
	
				
 		<div class="col-md-6 borderMod" id="rightDiv">
			
				<h4 class="col-md-10">Properties</h4>
 			<div class="col-md-8" style="margin-bottom: 5px;" id="test">
				<span class="col-md-6">Label</span>
					<div  id="labelTag">
						<input id="label" path="" class="col-md-5"/>
					</div>
					
		 	</div>
			
		 	<div class="col-md-8" style="margin-bottom: 5px;">
				<span class="col-md-6">Size</span>
				<div  id="lengthTag">
					<input id="length" path="" class="col-md-5"/>
				</div>
				
				
		 	</div>
			<div class="col-md-3">
				<span class="col-md-6">Ready Only</span>
				<div  id="readonlyTag">
					<input  id="readonly" path="" label="Readonly" type="checkbox" class="col-md-5"/>
					</div>
	  			
	  		</div>
		 	<div class="col-md-8">
				<span class="col-md-6">Type</span>
				<div  id="dataTypeTag">
				<input id="dataType" path="" class="col-md-5"/>
					</div>
				
	  		</div>
			<div class="col-md-3">
			<span class="col-md-6">Mandatory</span>
			<div  id="mandatoryTag">
			<input id="mandatory" path="" label="mandatory" type="checkbox" class="col-md-6"/>
					</div>
	  			
	  		</div>	
			<input type="hidden" id="selectedAttribute"/> 
			<input type="hidden" id="index"/> 
			<div class="col-md-offset-4 col-md-10" style="margin-bottom: 5px;">
	  			<button type="button" class="col-md-5" id="save">Save</button>
	  		</div>
		
 		</div>
		
	<div class="col-md-11 borderMod" id="middleDiv">
 			<div class="col-md-4 framework">
				<span class="col-md-6">Framework Layers</span>
				
				 	<form:select class="col-md-5" id="frameworkSelected" path="frameworkSelected">
					<form:option value="0">Select </form:option>
						<form:options items="${frameworkLayerList}" />
				</form:select> 
			
			 
	  		</div>
	  		<div class="col-md-4 framework">
				<span class="col-md-6">Business</span>
				<form:select class="col-md-5" id="businessSelected" path="businessSelected">
					<form:option value="0">Select </form:option>
						<form:options items="${businessLayerList}" />
				</form:select> 
	  		</div>
	  		<div class="col-md-4 framework">
				<span class="col-md-6">persistence</span>
				<form:select class="col-md-5" id="persistenceSelected" path="persistenceSelected">
					<form:option value="0">Select </form:option>
						<form:options items="${persistenceLayerList}" />
				</form:select> 
	  		</div>
 		</div>
 
 		
 		<div class="col-md-10 marginTop" id="db">
 			<span class="col-md-6">Database</span>
				<form:select class="col-md-5" id="databasetoGenerate" path="databasetoGenerate">
					<form:option value="0">Select </form:option>
						<form:options items="${databaseList}" />
				</form:select> 
 		</div>
 		<div class="col-md-10 marginTop">
 			<span  class="col-md-6">Destination Directory</span>
			<input type="text" class="col-md-5" name="destDirectory""/>
 		</div>
 		<div class="col-md-10 marginTop">
 			<span class="col-md-6">Filename</span>
				<input type="text" value="" class="col-md-5" name="fileName"/>
 		</div>
 		<div class="col-md-10 marginTop" style="margin-bottom: 5px;">
 			<button type="submit" class="col-md-offset-4 col-md-3">Generate</button>
 			<button type="button" class="col-md-offset-1 col-md-3">Generate & Deploy</button>
 		</div>
 	</div>
</body>
	
</form:form>
</html>