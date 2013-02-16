package vb6build.agent;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.inspections.InspectionReporter;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import jetbrains.buildServer.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import vb6build.common.PluginConstants;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 14/02/13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildRunner extends BuildServiceAdapter {

    private final ArtifactsWatcher myArtifactsWatcher;
    private final InspectionReporter myInspectionReporter;
    private File makeLogFile;

    public VB6BuildRunner(final ArtifactsWatcher artifactsWatcher, final InspectionReporter inspectionReporter){
        myArtifactsWatcher = artifactsWatcher;
        myInspectionReporter = inspectionReporter;
    }

    @Override
    public void beforeProcessStarted() throws RunBuildException {
        getLogger().progressMessage("Starting running VB6 Build");
    }

    @Override
    public void afterProcessFinished() throws RunBuildException {
        getLogger().progressMessage("Finished running VB6 Build");
    }



    @NotNull
    @Override
    public BuildFinishedStatus getRunResult(final int exitCode) {
        BuildFinishedStatus status = BuildFinishedStatus.FINISHED_FAILED;
        InputStream fis;
        BufferedReader br;
        String line;
        try
        {
            if (makeLogFile.exists())
            {
                fis = new FileInputStream(makeLogFile);
                br = new BufferedReader(new InputStreamReader(fis, Charset.defaultCharset()));
                while ((line = br.readLine()) != null) {
                    // Deal with the line
                    if (line.length() == 0 ||isNullOrWhitespace(line))
                    {
                        continue;
                    }
                    else
                    {
                        getLogger().progressMessage(line);
                        if (line.startsWith("Build of ") && line.endsWith(" succeeded."))
                        {
                            status = BuildFinishedStatus.FINISHED_SUCCESS;
                        }
                    }
                }
                makeLogFile.delete();
            }
            else
            {
                status = BuildFinishedStatus.FINISHED_WITH_PROBLEMS;
            }
        }
        catch (Exception e)
        {
            getLogger().error(e.toString());
            status = BuildFinishedStatus.FINISHED_FAILED;
        }
        return status;
    }

    @NotNull
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {

        try
        {

            final BuildParametersMap buildParameters = getBuildParameters();
            final List<String> arguments = new ArrayList<String>();

            makeLogFile = FileUtil.createTempFile(getBuildTempDirectory(), "make-", ".log", false);

            String makeType = "/make";
            if (getRunnerParameters().get(PluginConstants.Key_OutputType) == "exe")
            {
                    makeType = "/make";
            }
            else if (getRunnerParameters().get(PluginConstants.Key_OutputType) == "dll")
            {
                makeType = "/makedll";
            }
            else
            {
                makeType = "/make";
            }

            arguments.add(makeType);
            //arguments.add("C:\\Projects\\MentorTracking2012\\MentorTracking.vbp");
            arguments.add(getRunnerParameters().get(PluginConstants.Key_ProjectPath));
            arguments.add("/out");
            arguments.add(makeLogFile.getAbsolutePath());
            arguments.add("/outdir");
            //arguments.add("C:\\Published\\");
            arguments.add(getRunnerParameters().get(PluginConstants.Key_OutputPath));

            return new ProgramCommandLine() {
                @NotNull
                public String getExecutablePath() throws RunBuildException {
                    return getRunnerParameters().get(PluginConstants.Key_ExePath);
                    //return "C:\\Program Files\\Microsoft Visual Studio\\VB98\\VB6.EXE";
                }

                @NotNull
                public String getWorkingDirectory() throws RunBuildException {
                    return getCheckoutDirectory().getPath();
                }

                @NotNull
                public List<String> getArguments() throws RunBuildException {
                    return arguments;
                }

                @NotNull
                public Map<String, String> getEnvironment() throws RunBuildException {
                    return getBuildParameters().getEnvironmentVariables();
                }
            };
            /*
            return new SimpleProgramCommandLine(getBuildParameters().getEnvironmentVariables(),
                getCheckoutDirectory().getPath(),
                getBuildParameters().getAllParameters().get(PluginConstants.Key_ExePath),
                arguments);
             */
        }
        catch (Exception e)
        {
            final BuildProgressLogger logger = getBuild().getBuildLogger();
            logger.progressMessage(e.toString());
            return null;
        }

    }

    private boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s);

    }
    private boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}