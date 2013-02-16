package vb6build.server;

import com.intellij.openapi.util.text.*;
import vb6build.common.*;
import jetbrains.buildServer.serverSide.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class VB6BuildRunType extends RunType
{
    public VB6BuildRunType(@NotNull final RunTypeRegistry runTypeRegistry)
    {
        runTypeRegistry.registerRunType(this);
    }

    @NotNull
    @Override
    public String getType()
    {
        return PluginConstants.RunType;
    }

    @Override
    public String getDisplayName()
    {
        return PluginConstants.DisplayName;
    }

    @Override
    public String getDescription()
    {
        return PluginConstants.Description;
    }

    @Override
    public String describeParameters(@NotNull final Map<String, String> parameters)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("VB6 Executable Path:");
        sb.append(parameters.get(PluginConstants.Key_ExePath));
        sb.append(" ");
        sb.append("VB6 Project Path:");
        sb.append(parameters.get(PluginConstants.Key_ProjectPath));
        sb.append(" ");
        sb.append("Output Type:");
        sb.append(parameters.get(PluginConstants.Key_OutputType));
        sb.append(" ");
        sb.append("Output Path:");
        sb.append(parameters.get(PluginConstants.Key_OutputPath));

        return sb.toString();
    }

    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor()
    {
        return new PropertiesProcessor()
        {
            public Collection<InvalidProperty> process(Map<String, String> properties)
            {
                ArrayList<InvalidProperty> toReturn = new ArrayList<InvalidProperty>();
                if (!properties.containsKey(PluginConstants.Key_ExePath) || StringUtil.isEmpty(properties.get(PluginConstants.Key_ExePath)))
                    toReturn.add(new InvalidProperty(PluginConstants.Key_ExePath, "Please enter the path to the VB6 executable."));

                if (!properties.containsKey(PluginConstants.Key_ProjectPath) || StringUtil.isEmpty(properties.get(PluginConstants.Key_ProjectPath)))
                    toReturn.add(new InvalidProperty(PluginConstants.Key_ProjectPath, "Please enter a project path."));

                if (!properties.containsKey(PluginConstants.Key_OutputType) || StringUtil.isEmpty(properties.get(PluginConstants.Key_OutputType)))
                    toReturn.add(new InvalidProperty(PluginConstants.Key_OutputType, "Please select an output type."));

                if (!properties.containsKey(PluginConstants.Key_OutputPath) || StringUtil.isEmpty(properties.get(PluginConstants.Key_OutputPath)))
                    toReturn.add(new InvalidProperty(PluginConstants.Key_OutputPath, "Please enter an output path."));

                return toReturn;
            }
        };
    }

    @Override
    public String getEditRunnerParamsJspFilePath()
    {
        return "editVB6BuildParameters.jsp";
    }

    @Override
    public String getViewRunnerParamsJspFilePath()
    {
        return "viewVB6BuildParameters.jsp";
    }

    @Override
    public Map<String, String> getDefaultRunnerProperties()
    {
        return null;
    }
}
