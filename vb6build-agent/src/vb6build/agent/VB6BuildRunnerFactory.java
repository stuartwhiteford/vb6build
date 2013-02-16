package vb6build.agent;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.inspections.InspectionReporter;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.jetbrains.annotations.NotNull;
import vb6build.common.PluginConstants;

/**
 * Created with IntelliJ IDEA.
 * User: WHITEFS
 * Date: 14/02/13
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class VB6BuildRunnerFactory implements CommandLineBuildServiceFactory, AgentBuildRunnerInfo {

    private final ArtifactsWatcher myArtifactsWatcher;
    private InspectionReporter myInspectionsReporter;

    public VB6BuildRunnerFactory(@NotNull final ArtifactsWatcher artifactsWatcher,
                                 @NotNull final InspectionReporter inspectionsReporter) {
        myArtifactsWatcher = artifactsWatcher;
        myInspectionsReporter = inspectionsReporter;
    }

    @NotNull
    public String getType() {
        return PluginConstants.RunType;
    }

    public boolean canRun(@NotNull final BuildAgentConfiguration agentConfiguration) {
        return true;
    }

    @NotNull
    public CommandLineBuildService createService() {
        return new VB6BuildRunner(myArtifactsWatcher, myInspectionsReporter);
    }

    @NotNull
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return this;
    }

}