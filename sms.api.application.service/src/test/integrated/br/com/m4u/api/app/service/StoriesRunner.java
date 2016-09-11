package test.integrated.br.com.m4u.api.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.Steps;
import org.junit.Test;

import test.integrated.br.com.m4u.api.app.service.steps.SendSmsSteps;

public class StoriesRunner extends JUnitStories {

	@Override
	protected List<String> storyPaths() {

		return new StoryFinder().findPaths(
				CodeLocations.codeLocationFromClass(
					Thread.currentThread().getClass()).getFile(),
					Arrays.asList("**/*.story"), null);
	}

	@Override
	public Configuration configuration() {
		return new MostUsefulConfiguration()
				.useStoryLoader(
					new LoadFromClasspath(Thread.currentThread().getClass().getClassLoader()))
				
				.useStoryReporterBuilder(
					new StoryReporterBuilder()
						.withDefaultFormats()
						.withFormats(Format.HTML, Format.CONSOLE)
						.withRelativeDirectory("jbehave-report"));
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		ArrayList<Steps> stepFileList = new ArrayList<Steps>();
		stepFileList.add(new SendSmsSteps());

		return new InstanceStepsFactory(configuration(), stepFileList);
	}

	@Override
	@Test
	public void run() {
		try {
			super.run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}