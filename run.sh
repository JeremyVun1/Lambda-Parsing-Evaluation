if [ -z $1 ]
    then
        PCODE="tests/plus.ssu"
    else
        PCODE=$1
fi

if [ -z $2 ]
    then
        PARSER="SimpleSchemeParser.jj"
    else
        PARSER=$2
fi

javacc $PARSER
javac $(find -name "*.java") -d bin
java -classpath bin Main $PCODE