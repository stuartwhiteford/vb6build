<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<div class="parameter">
  VB6 Executable: <strong><props:displayValue name="exePath" /></strong>
</div>
<div class="parameter">
  Project Path: <strong><props:displayValue name="projectPath" /></strong>
</div>
<div class="parameter">
  Output Path: <strong><props:displayValue name="outputPath" /></strong>
</div>