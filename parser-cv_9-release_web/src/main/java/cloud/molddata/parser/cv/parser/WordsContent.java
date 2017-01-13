package cloud.molddata.parser.cv.parser;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsContent {
    private static String filePath= "/opt/tomcat/temp/dictionary/";
    private static final Set<String> fCOMMON_WORDS_EXCLUDE = new LinkedHashSet<>();

    /**Very common words to be excluded from searches.*/
    static {
        fCOMMON_WORDS_EXCLUDE.add("a");
        fCOMMON_WORDS_EXCLUDE.add("and");
        fCOMMON_WORDS_EXCLUDE.add("be");
        fCOMMON_WORDS_EXCLUDE.add("for");
        fCOMMON_WORDS_EXCLUDE.add("from");
        fCOMMON_WORDS_EXCLUDE.add("has");
        fCOMMON_WORDS_EXCLUDE.add("i");
        fCOMMON_WORDS_EXCLUDE.add("in");
        fCOMMON_WORDS_EXCLUDE.add("is");
        fCOMMON_WORDS_EXCLUDE.add("it");
        fCOMMON_WORDS_EXCLUDE.add("of");
        fCOMMON_WORDS_EXCLUDE.add("on");
        fCOMMON_WORDS_EXCLUDE.add("to");
        fCOMMON_WORDS_EXCLUDE.add("To");
        fCOMMON_WORDS_EXCLUDE.add("The");
        fCOMMON_WORDS_EXCLUDE.add("the");
        fCOMMON_WORDS_EXCLUDE.add("?");
        fCOMMON_WORDS_EXCLUDE.add("!");

        fCOMMON_WORDS_EXCLUDE.add("its");
        fCOMMON_WORDS_EXCLUDE.add("it");
        fCOMMON_WORDS_EXCLUDE.add("It");
        fCOMMON_WORDS_EXCLUDE.add("use");
        fCOMMON_WORDS_EXCLUDE.add(" use");
        fCOMMON_WORDS_EXCLUDE.add("us");
        fCOMMON_WORDS_EXCLUDE.add(" us");
        fCOMMON_WORDS_EXCLUDE.add(" as");
        fCOMMON_WORDS_EXCLUDE.add("as");
        fCOMMON_WORDS_EXCLUDE.add("nor");
        //fCOMMON_WORDS_EXCLUDE.add("or"); <-- nfluence on LANG and EDU in MALTSEV CV
        fCOMMON_WORDS_EXCLUDE.add("on");
        fCOMMON_WORDS_EXCLUDE.add("for");

        fCOMMON_WORDS_EXCLUDE.add("to");
        fCOMMON_WORDS_EXCLUDE.add(" to");
        fCOMMON_WORDS_EXCLUDE.add("To");
        fCOMMON_WORDS_EXCLUDE.add("The");
        fCOMMON_WORDS_EXCLUDE.add("the");
        fCOMMON_WORDS_EXCLUDE.add(" the");
        fCOMMON_WORDS_EXCLUDE.add("in");
        fCOMMON_WORDS_EXCLUDE.add("In");
        fCOMMON_WORDS_EXCLUDE.add("and");
        fCOMMON_WORDS_EXCLUDE.add("a");
        fCOMMON_WORDS_EXCLUDE.add("A");
        fCOMMON_WORDS_EXCLUDE.add("will");
        fCOMMON_WORDS_EXCLUDE.add("Will");
        fCOMMON_WORDS_EXCLUDE.add("Shall");
        fCOMMON_WORDS_EXCLUDE.add("shall");
        fCOMMON_WORDS_EXCLUDE.add("That");
        fCOMMON_WORDS_EXCLUDE.add("that");
        fCOMMON_WORDS_EXCLUDE.add("of");
        fCOMMON_WORDS_EXCLUDE.add("My");
        fCOMMON_WORDS_EXCLUDE.add("my");
        fCOMMON_WORDS_EXCLUDE.add("�");
        fCOMMON_WORDS_EXCLUDE.add("?");
        fCOMMON_WORDS_EXCLUDE.add("!");

        fCOMMON_WORDS_EXCLUDE.add("him");
        fCOMMON_WORDS_EXCLUDE.add("know");
        fCOMMON_WORDS_EXCLUDE.add("take");
        fCOMMON_WORDS_EXCLUDE.add("into");
        fCOMMON_WORDS_EXCLUDE.add("year");
        fCOMMON_WORDS_EXCLUDE.add("your");
        fCOMMON_WORDS_EXCLUDE.add("good");
        fCOMMON_WORDS_EXCLUDE.add("some");
        fCOMMON_WORDS_EXCLUDE.add("could");
        fCOMMON_WORDS_EXCLUDE.add("them");
        fCOMMON_WORDS_EXCLUDE.add("see");
        fCOMMON_WORDS_EXCLUDE.add("other");
        fCOMMON_WORDS_EXCLUDE.add("than");
        fCOMMON_WORDS_EXCLUDE.add("then");
        fCOMMON_WORDS_EXCLUDE.add("now");
        fCOMMON_WORDS_EXCLUDE.add("look");
        fCOMMON_WORDS_EXCLUDE.add("only");
        fCOMMON_WORDS_EXCLUDE.add("come");

        fCOMMON_WORDS_EXCLUDE.add("over");
        fCOMMON_WORDS_EXCLUDE.add("think");
        fCOMMON_WORDS_EXCLUDE.add("also");
        fCOMMON_WORDS_EXCLUDE.add("back");
        fCOMMON_WORDS_EXCLUDE.add("after");
        fCOMMON_WORDS_EXCLUDE.add("two");
        fCOMMON_WORDS_EXCLUDE.add("how");
        fCOMMON_WORDS_EXCLUDE.add("our");
        fCOMMON_WORDS_EXCLUDE.add("work");
        fCOMMON_WORDS_EXCLUDE.add("first");
        fCOMMON_WORDS_EXCLUDE.add("well");
        fCOMMON_WORDS_EXCLUDE.add("way");
        fCOMMON_WORDS_EXCLUDE.add("even");
        fCOMMON_WORDS_EXCLUDE.add("new");
        fCOMMON_WORDS_EXCLUDE.add("want");
        fCOMMON_WORDS_EXCLUDE.add("because");
        fCOMMON_WORDS_EXCLUDE.add("any");
        fCOMMON_WORDS_EXCLUDE.add("these");
        fCOMMON_WORDS_EXCLUDE.add("give");
        fCOMMON_WORDS_EXCLUDE.add("day");
        fCOMMON_WORDS_EXCLUDE.add("most");

        fCOMMON_WORDS_EXCLUDE.add("find");
        fCOMMON_WORDS_EXCLUDE.add("tell");
        fCOMMON_WORDS_EXCLUDE.add("ask");
        fCOMMON_WORDS_EXCLUDE.add("seem");
        fCOMMON_WORDS_EXCLUDE.add("feel");
        fCOMMON_WORDS_EXCLUDE.add("try");
        fCOMMON_WORDS_EXCLUDE.add("leave");
        fCOMMON_WORDS_EXCLUDE.add("call");
        fCOMMON_WORDS_EXCLUDE.add("last");
        fCOMMON_WORDS_EXCLUDE.add("three");
        fCOMMON_WORDS_EXCLUDE.add("next");
        fCOMMON_WORDS_EXCLUDE.add("million");
        fCOMMON_WORDS_EXCLUDE.add("four");
        fCOMMON_WORDS_EXCLUDE.add("five");
        fCOMMON_WORDS_EXCLUDE.add("second");
        fCOMMON_WORDS_EXCLUDE.add("six");
        fCOMMON_WORDS_EXCLUDE.add("third");
        fCOMMON_WORDS_EXCLUDE.add("billion");
        fCOMMON_WORDS_EXCLUDE.add("hundred");
        fCOMMON_WORDS_EXCLUDE.add("thousand");
        fCOMMON_WORDS_EXCLUDE.add("seven");
        fCOMMON_WORDS_EXCLUDE.add("eight");
        fCOMMON_WORDS_EXCLUDE.add("ten");
        fCOMMON_WORDS_EXCLUDE.add("nine");
        fCOMMON_WORDS_EXCLUDE.add("dozen");
        fCOMMON_WORDS_EXCLUDE.add("fourth");
        fCOMMON_WORDS_EXCLUDE.add("twenty");
        fCOMMON_WORDS_EXCLUDE.add("fifth");
        fCOMMON_WORDS_EXCLUDE.add("thirty");
        fCOMMON_WORDS_EXCLUDE.add("while");
        fCOMMON_WORDS_EXCLUDE.add("where");
        fCOMMON_WORDS_EXCLUDE.add("though");
        fCOMMON_WORDS_EXCLUDE.add("since");
        fCOMMON_WORDS_EXCLUDE.add("until");
        fCOMMON_WORDS_EXCLUDE.add("whether");
        fCOMMON_WORDS_EXCLUDE.add("before");
        fCOMMON_WORDS_EXCLUDE.add("although");

        fCOMMON_WORDS_EXCLUDE.add("once");
        fCOMMON_WORDS_EXCLUDE.add("unless");
        fCOMMON_WORDS_EXCLUDE.add("except");
        fCOMMON_WORDS_EXCLUDE.add("something");
        fCOMMON_WORDS_EXCLUDE.add("nothing");
        fCOMMON_WORDS_EXCLUDE.add("anything");
        fCOMMON_WORDS_EXCLUDE.add("himself");
        fCOMMON_WORDS_EXCLUDE.add("everything");
        fCOMMON_WORDS_EXCLUDE.add("someone");
        fCOMMON_WORDS_EXCLUDE.add("themselves");
        fCOMMON_WORDS_EXCLUDE.add("everyone");
        fCOMMON_WORDS_EXCLUDE.add("itself");
        fCOMMON_WORDS_EXCLUDE.add("anyone");
        fCOMMON_WORDS_EXCLUDE.add("myself");
        fCOMMON_WORDS_EXCLUDE.add("through");
        fCOMMON_WORDS_EXCLUDE.add("between");
        fCOMMON_WORDS_EXCLUDE.add("against");
        fCOMMON_WORDS_EXCLUDE.add("during");
        fCOMMON_WORDS_EXCLUDE.add("without");
        fCOMMON_WORDS_EXCLUDE.add("under");
        fCOMMON_WORDS_EXCLUDE.add("around");
        fCOMMON_WORDS_EXCLUDE.add("among");
        fCOMMON_WORDS_EXCLUDE.add("more");
        fCOMMON_WORDS_EXCLUDE.add("here");
        fCOMMON_WORDS_EXCLUDE.add("very");
        fCOMMON_WORDS_EXCLUDE.add("down");
        fCOMMON_WORDS_EXCLUDE.add("still");
        fCOMMON_WORDS_EXCLUDE.add("too");
        fCOMMON_WORDS_EXCLUDE.add("never");
        fCOMMON_WORDS_EXCLUDE.add("own");
        fCOMMON_WORDS_EXCLUDE.add("old");
        fCOMMON_WORDS_EXCLUDE.add("right");
        fCOMMON_WORDS_EXCLUDE.add("small");
        fCOMMON_WORDS_EXCLUDE.add("large");
        fCOMMON_WORDS_EXCLUDE.add("early");
        fCOMMON_WORDS_EXCLUDE.add("young");
        fCOMMON_WORDS_EXCLUDE.add("important");
        fCOMMON_WORDS_EXCLUDE.add("few");
        fCOMMON_WORDS_EXCLUDE.add("bad");
        fCOMMON_WORDS_EXCLUDE.add("same");
        fCOMMON_WORDS_EXCLUDE.add("able");
        fCOMMON_WORDS_EXCLUDE.add("thing");
        //fCOMMON_WORDS_EXCLUDE.add(" ");
    }

    public static Set<String> getfCOMMON_WORDS_EXCLUDE() {
        return fCOMMON_WORDS_EXCLUDE;
    }

    public static boolean isExcludeWord(String token) {
        return fCOMMON_WORDS_EXCLUDE.contains(token);
    }
    //-----------------------------------------------------------------------

    private static final Set<String> fCOMMON_WORDS_SKILLS = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_SKILLS.add(".*\\b[Qq]ualification(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b.*db(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b[Ss]kill(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b[Aa]gile(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b[Aa]jax\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b[Jj]ava\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b.*bean.*\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\boop\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bxml\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b.*sql\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bj2ee\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bjsp\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bstruts\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bjstl\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bcss\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bjaxb\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bejb\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bscala\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bhtml\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bsoap\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bdtd\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bwsdl\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bxsd\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bcassandra\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\boracle\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bunix\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bcisco\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bweb\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bservice(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\b.*script(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bscripting(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bhadop(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bservlet(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bpython(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\buml\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bperl\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bexperience(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bprogram.*\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bencrypt.*\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bapache.*\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\btomcat.*\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bpattern(.?|s)\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bdao\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bhibernate\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bmvc\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bmaven\\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\\bant\\b.*");
        //------------------------------------------
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][+][+]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][#]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Oo][Bb][Jj][Ee][Cc][Tt][Ii][Vv][Ee][-][Cc]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Hh][Pp]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Yy][Tt][Hh][Oo][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Rr][Uu][Bb][Yy]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Jj][Aa][Vv][Aa][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Jj][Ss]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Qq][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Ll][/][Ss][Qq][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Hh][Tt][Mm][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Ss][Ss]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Cc][Aa][Ll][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Cc][Tt][Ii][Oo][Nn][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Dd][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Ll][Gg][Oo][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Tt][Ee][Jj][Ii][ ][Pp][Xx]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Bb][Aa][Ss][Ii][Cc]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Ee][Yy][Ll][Oo][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Ll][Ii][Pp][Pp][Ee][Rr]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Ll][Ee][Oo]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Oo][Bb][Oo][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Oo][Bb][Rr][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ll][Ii][Ss][Pp]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Rr][Yy][Ss][Tt][Aa][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Uu][Rr][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Dd][Aa][Ss][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Dd][Ee][Ll][Pp][Hh][Ii]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Dd][Ii][Bb][Oo][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Dd][Yy][Ll][Aa][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ee][Ii][Ff][Ff][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Aa][Tt][Hh][Ee][Rr]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Uu][Bb][Ee][Rr][Cc][Oo][Dd][Ee]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ee][Rr][Ll][Aa][Nn][Gg]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][#]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Aa][Cc][Tt][Oo][Rr]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Oo][Rr][Tt][Hh]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Oo][Rr][Tt][Rr][Aa][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Gg][Aa][Uu][Ss][Ss]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Gg][Oo]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Gg][Oo][Ss][Uu]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Gg][Rr][Oo][Oo][Vv][Yy]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Hh][Aa][Ss][Kk][Ee][Ll][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Hh][Aa][Rr][Bb][Oo][Uu][Rr]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Jj][Aa][Vv][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Jj][Oo][Vv][Ii][Aa][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ll][Aa][Bb][Vv][Ii][Ee][Ww]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Mm][Ee][Rr][Cc][Uu][Rr][Yy]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Nn][Ee][Mm][Ee][Rr][Ll][Ee]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Nn][Ii][Mm]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Aa][Ss][Cc][Aa][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Rr][Pp][Gg]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Rr][Uu][Ss][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Ee][Qq][Uu][Ee][Nn][Cc][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Ii][Mm][Uu][Ll][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Ww][Ii][Ff][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Mm][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Oo][Cc][Aa][Mm][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Tt][Uu][Rr][Ii][Nn][Gg]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Vv][Aa][Ll][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Oo][Xx][Pp][Rr][Oo]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Rr][Oo][Ll][Oo][Gg]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Xx][+][+]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Xx][#]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Zz][+][+]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Xx][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Vv][Bb][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Mm][Xx]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ww][Ee][Bb][Dd][Nn][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Cc][Tt][Ii][Oo][Nn][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ee][Cc][Mm][Aa][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Jj][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Vv][Bb][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Nn][Gg][Ee][Ll][Ss][Cc][Rr][Ii][Pp][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Hh]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ee][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ll][Uu][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Mm][Ii][Nn][Ii][Dd]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Qq][Uu][Ii][Rr][Rr][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Tt][Cc][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][+]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Nn][Aa][Ll][Yy][Tt][Ii][Cc][Aa]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Aa][Pp][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Hh][Aa][Pp][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Oo][Rr][Tt][Rr][Aa][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ff][Rr][Ee][Ee][Mm][Aa][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Gg][Aa][Uu][Ss][Ss]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Mm][Aa][Tt][Ll][Aa][Bb]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Oo][Cc][Tt][Aa][Vv][Ee]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][--][Ll][Aa][Nn][Gg]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Ee][Qq][Uu][Ee][Nn][Cc][Ee][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Zz][Pp][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ii][Dd][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ww][Oo][Ll][Ff][Rr][Aa][Mm]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Xx][Mm][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[44][Dd][Oo][Ss]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Bb][Aa][Ss][Hh]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Hh][Aa][Ii][Nn]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Ll][Ii][Ss][Tt]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Cc][Mm][Ss][ ][Ee][Xx][Ee][Cc]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ee][Xx][Ee][Cc]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Pp][Oo][Ww][Ee][Rr][Ss][Hh][Ee][Ll][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Zz][Ss][Hh]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Tt][Aa][Cc][Ll]\b.*");
        fCOMMON_WORDS_SKILLS.add(".*\b[Ss][Hh]\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("SKILLS.txt", fCOMMON_WORDS_SKILLS);
    }

    public static Set<String> getfCOMMON_WORDS_SKILLS() {
        return fCOMMON_WORDS_SKILLS;
    }

    public static boolean is_WORDS_SKILLS(String token) {
        for (String expression : fCOMMON_WORDS_SKILLS) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_SKILLS(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_SKILLS);
        writeToFile("SKILLS.txt", words);
    }

    //-----------------------------------------------------------------------
    private static final Set<String> fCOMMON_WORDS_EDUCATION = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_EDUCATION.add(".*\\beducation(.?|s)\\b.*");
        fCOMMON_WORDS_EDUCATION.add(".*\\bmaster(.?|s)\\b.*");
        fCOMMON_WORDS_EDUCATION.add(".*\\bdegree(.?|s)\\b.*");
        fCOMMON_WORDS_EDUCATION.add(".*\\bbachelor(.?|s)\\b.*");
        fCOMMON_WORDS_EDUCATION.add(".*\\bm.s.\\b.*");
        fCOMMON_WORDS_EDUCATION.add(".*\\bphd.*\\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("EDUCATION.txt", fCOMMON_WORDS_EDUCATION);
    }

    public static Set<String> getfCOMMON_WORDS_EDUCATION() {
        return fCOMMON_WORDS_EDUCATION;
    }

    public static boolean is_WORDS_EDUCATION(String token) {
        for (String expression : fCOMMON_WORDS_EDUCATION) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_EDUCATION(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_EDUCATION);
        writeToFile("EDUCATION.txt", words);
    }
    //-----------------------------------------------------------------------

    private static final Set<String> fCOMMON_WORDS_LANGUAGE = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_LANGUAGE.add(".*\\blanguage(.?|s)\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\brus.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bukr.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bdutch.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\beng.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bfra.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bisp.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bflow.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bflue.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\bnative.*\\b.*");
        fCOMMON_WORDS_LANGUAGE.add(".*\\b.*intermediate.*\\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("LANGUAGE.txt", fCOMMON_WORDS_LANGUAGE);
    }

    public static Set<String> getfCOMMON_WORDS_LANGUAGE() {
        return fCOMMON_WORDS_LANGUAGE;
    }

    public static boolean is_WORDS_LANGUAGE(String token) {
        for (String expression : fCOMMON_WORDS_LANGUAGE) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_LANGUAGE(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_LANGUAGE);
        writeToFile("LANGUAGE.txt", words);
    }
    //-----------------------------------------------------------------------

    private static final Set<String> fCOMMON_WORDS_EXPERIENCE = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bproject(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bclient(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bserver(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bwork(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bexperience(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bpresent(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bdesription(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\brole(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bsenior\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bjunior\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bteam\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\blead.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bdevelop.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\banalyz.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bbusine.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bdesign.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\buse.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bimplement.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bwork.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bapplication(.?|s)\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bapp.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bgam.*\\b.*");
        fCOMMON_WORDS_EXPERIENCE.add(".*\\bclient.*\\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("EXPERIENCE.txt", fCOMMON_WORDS_EXPERIENCE);
    }

    public static Set<String> getfCOMMON_WORDS_EXPERIENCE() {
        return fCOMMON_WORDS_EXPERIENCE;
    }

    public static boolean is_WORDS_EXPERIENCE(String token) {
        for (String expression : fCOMMON_WORDS_EXPERIENCE) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_EXPERIENCE(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_EXPERIENCE);
        writeToFile("EXPERIENCE.txt", words);
    }
    //-----------------------------------------------------------------------
    public static String is_NAME_PARSE(String text) {

        Pattern pName0 = Pattern.compile("([A-Z][a-z]+\\s[A-Z]\\.((|\\s)[A-Z]\\.|\\s))"); //Kanagava V.B.
        Pattern pName1 = Pattern.compile("([A-Z][a-z]+\\s){2,}"); // Ivan Petrovich and Ivan Petro Borisich
        Pattern pName2 = Pattern.compile("([A-Z][a-z]+(|\\s)){2,}"); // Ivan Petrovich and Ivan Petro Borisich
        Pattern pName3 = Pattern.compile("([A-Z][a-z]+-[A-Z][a-z]+\\s([A-Z][a-z]+))"); //Ikhe-Abdu Adalahi

        Matcher mName0 = pName0.matcher(text);
        Matcher mName1 = pName1.matcher(text);
        Matcher mName2 = pName2.matcher(text);
        Matcher mName3 = pName3.matcher(text);

        String resName = "";

        if (mName3.find())
            resName = mName3.group();
        if (mName2.find())
            resName = mName2.group();
        if (mName1.find())
            resName = mName1.group();
        if (mName0.find())
            resName = mName0.group();

        return resName;
    }
    //-----------------------------------------------------------------------
    private static final Set<Pattern> fCOMMON_PHONE_PARSE = new LinkedHashSet<>();

    static {
        fCOMMON_PHONE_PARSE.add(Pattern.compile("(?:\\+|[0-9] ?){6,14}[0-9]")); //all without braces
        fCOMMON_PHONE_PARSE.add(Pattern.compile("((.?|\\+)\\d{2}[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}")); //all with braces
    }

    public static Set<Pattern> getfCOMMON_PHONE_PARSE() {
        return fCOMMON_PHONE_PARSE;
    }

    public static String is_PHONE_PARSE(String text) {
        String resNumber = "";
        if (text == null) // when contacts block consist null in phone block
            return resNumber;
        for (Pattern pattern : fCOMMON_PHONE_PARSE) {
            Matcher phone = pattern.matcher(text);
            if (phone.find() && resNumber.length() < phone.group().length())
                resNumber = phone.group();
        }
        return (resNumber.equals("")) ? null : resNumber;
    }

    //------------------------------------------------------------------------------
    private static final Pattern Mail = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");

    public static String is_MAIL_PARSE(String text) {
        Matcher mMail = Mail.matcher(text);

        if (mMail.find())
            return mMail.group();
        return null;
    }

    //------------------------------------------------------------------------------
    public static String is_Location(String phone) {
        if (phone != null && is_PHONE_PARSE(phone) != null) {
            SeacrherRegByPh seacrherRegByPh = new SeacrherRegByPh();
            return seacrherRegByPh.searcherCountryCode(phone)[1];
        } else
            return "UNKNOWN LOCATION";
    }

    private static final Set<String> fCOMMON_WORDS_TRAININGS = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_TRAININGS.add(".*\\b[Tt][Rr][Aa][Ii][Nn][Ii][Nn][Gg](.?|s)\\b.*");
        fCOMMON_WORDS_TRAININGS.add(".*\\b[Cc][En][Nn][Tt][Rr](.?|[Ee])\\b.*");
        fCOMMON_WORDS_TRAININGS.add(".*\\b[Cc][En][Rr][Tt][Ii][Ff][Ii][Cc][Aa][Tt](.?|[Ee])\\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("TRAININGS.txt", fCOMMON_WORDS_TRAININGS);
    }

    public static Set<String> getfCOMMON_WORDS_TRAININGS() {
        return fCOMMON_WORDS_SKILLS;
    }

    public static boolean is_WORDS_TRAININGS(String token) {
        for (String expression : fCOMMON_WORDS_TRAININGS) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_TRAININGS(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_TRAININGS);
        writeToFile("TRAININGS.txt", words);
    }

    private static final Set<String> fCOMMON_WORDS_OBJECTIVE = new LinkedHashSet<>();

    static {
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Ca][Aa][Rr][Ee][Ee][Rr](.?|s)\\b.*");
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Oo][Bb][Jj][Ee][Cc][Tt][Ii][Vv][Ee](.?|[Ee])\\b.*");
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Oo][Bb][Tt][Aa][Ii][Nn](.?|[Ee])\\b.*");
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Pp][Oo][Ss][Ii][Tt][Ii][Oo][Nn](.?|[Ee])\\b.*");
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Pp][Ee][Rr][Ss][Oo][Nn][Aa][Ll](.?|[Ee])\\b.*");
        fCOMMON_WORDS_OBJECTIVE.add(".*\\b[Gg][Rr][Oo][Ww][Tt][Hh](.?|[Ee])\\b.*");
        //-------------------------------------------------------
        addWordsFromFileToSet("OBJECTIVE.txt", fCOMMON_WORDS_OBJECTIVE);
    }

    public static Set<String> getfCOMMON_WORDS_OBJECTIVE() {
        return fCOMMON_WORDS_OBJECTIVE;
    }

    public static boolean is_WORDS_OBJECTIVE(String token) {
        for (String expression : fCOMMON_WORDS_OBJECTIVE) {
            if (token.toLowerCase().matches(expression))
                return true;
        }
        return false;
    }

    public static void add_WORD_OBJECTIVE(ArrayList<String> words) {
        addWordToSET(words, fCOMMON_WORDS_OBJECTIVE);
        writeToFile("OBJECTIVE.txt", words);
    }
    //--------------------------------------------------------------------

    private static void addWordToSET(ArrayList<String> words, Set<String> set) {
        for(String word:words) {
            set.add(makeRegular(word));
        }
    }

    private static void addWordsFromFileToSet(String srcFile, Set<String> set) {
        ArrayList<String> words = readFromFile(srcFile);
        for (String word : words) {
            set.add(makeRegular(word));
        }
    }

    private static String makeRegular(String word) {
        StringBuilder sb = new StringBuilder();
        char[] array = word.toCharArray();
        //------------------------------------
        sb.append(".*\\b");
        for (int i = 0; i < array.length; ++i) {
            sb.append("[");
            sb.append(Character.toUpperCase(array[i]));
            sb.append(Character.toLowerCase(array[i]));
            sb.append("]");
        }
        sb.append("(.?|[Ss])\\b.*");
        return sb.toString();
    }

    private static void writeToFile(String srcFile,ArrayList<String> words) {
        String path = filePath + srcFile;
        File file = null;
        BufferedWriter bw = null;
        try {
            file = new File(path);
            bw = new BufferedWriter(new FileWriter(file, true));
            for(String word:words) {
                bw.append(word);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList readFromFile(String srcFile) {
        ArrayList<String> words = new ArrayList<String>();
        String path = filePath + srcFile;
        File file = null;
        BufferedReader br = null;
        try {
            file = new File(path);
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                words.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return words;
        }
    }
}

