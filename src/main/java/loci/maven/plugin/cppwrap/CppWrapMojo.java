//
// CppWrapMojo.java
//

/*
C++ Wrapper Maven plugin for generating C++ proxy classes for a Java library.

Copyright (c) 2011, UW-Madison LOCI
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the UW-Madison LOCI nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package loci.maven.plugin.cppwrap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loci.jar2lib.Jar2Lib;
import loci.jar2lib.VelocityException;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Goal which creates a C++ project wrapping a Maven Java project.
 *
 * Portions of this mojo we adapted from the exec-maven-plugin's ExecJavaMojo,
 *
 * @author Curtis Rueden
 *
 * @goal wrap
 */
public class CppWrapMojo extends AbstractMojo {

	/**
	 * The Maven project to wrap.
	 *
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * Path to conflicts list of Java constants to rename,
	 * to avoid name collisions.
	 *
	 * @parameter expression="${cppwrap.conflictsFile}"
	 *   default-value="src/main/cppwrap/conflicts.txt"
	 */
	private File conflictsFile;

	/**
	 * Path to header file to prepend to each C++ source file.
	 *
	 * @parameter expression="${cppwrap.headerFile}"
	 *   default-value="LICENSE.txt"
	 */
	private File headerFile;

	/**
	 * Path to output folder for C++ project.
	 *
	 * @parameter expression="${cppwrap.outputDir}"
	 *   default-value="target/cppwrap"
	 */
	private File outputDir;

	@Override
	public void execute() throws MojoExecutionException {
		final String artifactId = project.getArtifactId();

		final String projectId = artifactId.replaceAll("[^\\w\\-]", "_");
		final String projectName = project.getName();
		final List<String> libraryJars = getLibraryJars();
		final List<String> classpathJars = getClasspathJars();
		final String conflictsPath = conflictsFile.exists() ?
			conflictsFile.getPath() : null;
		final String headerPath = headerFile.exists() ?
			headerFile.getPath() : null;
		final String outputPath = outputDir.getPath();

		final Jar2Lib jar2lib = new Jar2Lib() {
			@Override
			protected void log(String message) {
				getLog().info(message);
			}
		};
		jar2lib.setProjectId(projectId);
		jar2lib.setProjectName(projectName);
		jar2lib.setLibraryJars(libraryJars);
		jar2lib.setClasspathJars(classpathJars);
		jar2lib.setConflictsPath(conflictsPath);
		jar2lib.setHeaderPath(headerPath);
		jar2lib.setOutputPath(outputPath);
		try {
			jar2lib.execute();
		}
		catch (IOException e) {
			throw new MojoExecutionException("Error invoking jar2lib", e);
		}
		catch (VelocityException e) {
			throw new MojoExecutionException("Error invoking jar2lib", e);
		}
	}

	private List<String> getLibraryJars() throws MojoExecutionException {
		final List<String> jars = new ArrayList<String>();

		// add project artifact
		final File projectArtifact = project.getArtifact().getFile();
		if (projectArtifact == null || !projectArtifact.exists()) {
			throw new MojoExecutionException("Must execute package target first.");
		}
		jars.add(projectArtifact.getPath());

		return jars;
	}

	private List<String> getClasspathJars() {
		final List<String> jars = new ArrayList<String>();

		// add project runtime dependencies
		final List<Artifact> artifacts = project.getRuntimeArtifacts();
		for (final Artifact classPathElement : artifacts) {
			jars.add(classPathElement.getFile().getPath());
		}

		return jars;
	}

}
