<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<l:settingsGroup title="Runner Parameters">
    <tr>
        <th>
            <label for="exePath">VB6 Executable: </label>
        </th>
        <td>
            <props:textProperty name="exePath" style="width: 30em;" />
            <span class="error" id="error_exePath"></span>
            <span class="smallNote">The path to the Visual Basic 6 executable i.e. C:\Program Files\Microsoft Visual Studio\VB98\VB6.EXE</span>
        </td>
    </tr>
    <tr>
        <th>
            <label for="projectPath">Project Path: </label>
        </th>
        <td>
            <props:textProperty name="projectPath" style="width: 30em;" />
            <span class="error" id="error_projectPath"></span>
            <span class="smallNote">The path and file of the Visual Basic 6 project i.e. C:\Projects\VBProjectName.vbp</span>
        </td>
    </tr>
    <tr>
        <th>
            <label for="outputType">Output Type: </label>
        </th>
        <td>
            <props:selectProperty name="outputType">
                <props:option value="exe"><c:out value="Executable (EXE)"/></props:option>
                <props:option value="dll"><c:out value="Dynamic-link Library (DLL)"/></props:option>
            </props:selectProperty>
            <span class="error" id="error_outputType"></span>
            <span class="smallNote">The output type of the project.</span>
        </td>
    </tr>
    <tr>
        <th>
            <label for="outputPath">Output Path: </label>
        </th>
        <td>
            <props:textProperty name="outputPath" style="width: 30em;" />
            <span class="error" id="error_outputPath"></span>
            <span class="smallNote">The path to the output folder i.e. C:\Published\</span>
        </td>
    </tr>
</l:settingsGroup>