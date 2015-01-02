Matroska Batch
==============

![Screenshot of main window](screenshot2.png)

![Screenshot showing test files](screenshot1.png)

MatroskaBatch is used for batch muxing of multimedia files to Matroska
container. If you not using
[mkvtoolnix](https://www.bunkus.org/videotools/mkvtoolnix/) (or specifically
`mkvmerge`), you probably don't need MatroskaBatch.

MatroskaBatch automagically organises files by their _names_ and mux them to
the closest video file. This smart matching provides the easiest way to add
audio or subtitles to existing Matroska files. It also handles well muxing
multiple non-Matroska files together (i.e. video, audio and subtitle track) as
long as they have similar names. Manual file-by-file mode is also available
for fine tuning.


Building
--------

This project is [maven](apache/maven), but one of it's dependencies is not
present in maven repository. You have to add it to your local repo manually.
See [java-string-similarity](rrice/java-string-similarity) or just go with:

	git clone https://github.com/rrice/java-string-similarity.git
	cd java-string-similarity
	mvn install

Now you can build MatroskaBatch as usual.


Installation
------------

MatroskaBatch is Java program. You need Java 8 (Oracle Java or OpenJDK) and
Java FX to run MatroskaBatch. On debian-based systems run

	sudo aptitude install openjdk-8-jre openjfx

MatroskaBatch uses `mkvmerge` for muxing so you need
[mkvtoolnix](https://www.bunkus.org/videotools/mkvtoolnix/) on your system. It
is packaged for most systems. On debian distros run

	sudo aptitude install mkvtoolnix

`mkvmerge` must be installed in `$PATH`, which is default if you used
package manager.

Now you are ready to run MatroskaBatch

	java -jar MatroskaBatch.jar


Copyright
---------

Unless otherwise stated, this project is licensed under terms of MIT licence, see [LICENSE](LICENSE)
for details.

Currently [icons](src/main/resources/icons/), which are derived from GNOME
Icon Theme are licensed under
[CreativeCommons](src/main/resources/icons/LICENSE) licence. 
