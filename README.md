# BlockNoteDesktop
BlockNote desktop app JavaFX

# Windows build information
## build Java8
> "%JAVA_HOME%/bin/javac" -sourcepath ./src/main/java -d bin -classpath lib/* ./src/main/java/stan/block/note/Main.java

## build Java7
> "%JAVA_HOME%/bin/javac" -sourcepath ./src/main/java -d bin -classpath "%JAVA_HOME%/jre/lib/jfxrt.jar";lib/* ./src/main/java/stan/block/note/Main.java

## build_css
> "%JAVA_HOME%/bin/javapackager" -createbss -srcdir ./src/main/css -outdir bin/css -srcfiles theme.css -v

## run Java8
> "%JAVA_HOME%/bin/java" -classpath lib/*;bin stan.block.note.Main

## run Java7
> "%JAVA_HOME%/bin/java" -classpath "%JAVA_HOME%/jre/lib/jfxrt.jar";lib/*;bin stan.block.note.Main