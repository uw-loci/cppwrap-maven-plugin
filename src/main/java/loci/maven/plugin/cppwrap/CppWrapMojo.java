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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which creates a C++ project wrapping a Maven Java project.
 *
 * @author Curtis Rueden
 *
 * @goal cppwrap
 */
public class CppWrapMojo extends AbstractMojo {

	private static final String CPPWRAP_DIR = "src/main/cppwrap/";

	/**
	 * Path to conflicts list of Java constants to rename,
	 * to avoid name collisions.
	 *
	 * @parameter expression="${cppwrap.conflictsFile}"
	 *   default-value="src/main/cppwrap/conflicts.txt"
	 */
	private String conflictsFile = CPPWRAP_DIR + "conflicts.txt";

	/**
	 * Path to header file to prepend to each C++ source file.
	 *
	 * @parameter expression="${cppwrap.conflictsFile}"
	 *   default-value="src/main/cppwrap/conflicts.txt"
	 */
	private String headerFile = CPPWRAP_DIR + "header.txt";

	/**
	 * TODO
	 */
	public void execute() throws MojoExecutionException {
		getLog().info("Hello, world.");
		// TODO throw MojoExecutionException to cause BUILD ERROR message
		// TODO throw MojoFailureException to cause BUILD FAILURE message
	}

}
