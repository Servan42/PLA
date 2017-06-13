/* Generated By:JavaCC: Do not edit this line. ComportementRobots.java */
package Parsing;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import Engine.*;

public class ComportementRobots implements ComportementRobotsConstants {
  public ComportementRobots(String s)
  {
    this (new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
  }

  public static void main(String args []) throws ParseException
  {
    ComportementRobots parser = new ComportementRobots("*{P;P}");
    while (true)
    {
      System.out.println("Reading from standard input...");
      System.out.println("Enter an expression :");
      //try
      //{
      //ComportementRobots.loop();
      //System.out.println(a.toString());
      /*}
      catch (Exception e)
      {
        System.out.println("NOK.");
        System.out.println(e.getMessage());
        ComportementRobots.ReInit(System.in);
      }
      catch (Error e)
      {
        System.out.println("Oops.");
        System.out.println(e.getMessage());
        break;
      }*/
    }
  }

/*
TOKEN :
{
  < ACTION : < LETTER > >
| < #LETTER : [ "H", "K", "O", "J", "P" ] >
}
*/
  static final public Arbre loop() throws ParseException {
  Arbre fd;
    jj_consume_token(STAR);
    jj_consume_token(AO);
    fd = comp();
    jj_consume_token(AF);
    {if (true) return new Arbre(new Star(), null, fd);}
    throw new Error("Missing return statement in function");
  }

  static final public Arbre comp() throws ParseException {
  Arbre fg, fd;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
      fg = action();
      fd = op();
    if (fd != null)
    {
      fd.AjouterFilsGauche(fg);
      {if (true) return fd;}
    }
    else {if (true) return fg;}
      break;
    case STAR:
      fg = loop();
      fd = op();
    if (fd != null)
    {
      fd.AjouterFilsGauche(fg);
      {if (true) return fd;}
    }
    else
    {if (true) return fg;}
      break;
    case AO:
      jj_consume_token(AO);
      fg = comp();
      jj_consume_token(AF);
      fd = op();
    if (fd != null)
    {
      fd.AjouterFilsGauche(fg);
      {if (true) return fd;}
    }
    else
    {
      {if (true) return fg;}
    }
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Arbre action() throws ParseException {
  Arbre retour;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 12:
      jj_consume_token(12);
      retour = dp(new Hit());
    {if (true) return retour;}
      break;
    case 13:
      jj_consume_token(13);
      retour = dp(new Kamikaze());
    {if (true) return retour;}
      break;
    case 14:
      jj_consume_token(14);
      retour = dp(new Others());
    {if (true) return retour;}
      break;
    case 15:
      jj_consume_token(15);
      retour = dp(new Rapport());
    {if (true) return retour;}
      break;
    case 16:
      jj_consume_token(16);
      retour = dp(new Protect());
    {if (true) return retour;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Arbre dp(Operateurs o) throws ParseException {
  Arbre retour;
    retour = new Arbre(o);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DP:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_1;
      }
      jj_consume_token(DP);
      retour = new Arbre(new DeuxPoints(), null, retour);
    }
    {if (true) return retour;}
    throw new Error("Missing return statement in function");
  }

  static final public Arbre op() throws ParseException {
  Arbre fd, fg;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PREF:
      jj_consume_token(PREF);
      fd = comp();
    {if (true) return new Arbre(new Preference(), null, fd);}
      break;
    default:
      jj_la1[3] = jj_gen;
      fg = ou();
      fd = pv();
    if (fd != null)
    {
      if (fg != null)
      fd.AjouterFilsDroit(fg);
      {if (true) return fd;}
    }
    else
    {
      {if (true) return fg;}
    }
    }
    throw new Error("Missing return statement in function");
  }

  static final public Arbre pv() throws ParseException {
  Arbre fd, retour;
    retour = new Arbre(new PointVirgule());
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PV:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_2;
      }
      jj_consume_token(PV);
      fd = comp();
      retour.AjouterFilsDroit(fd);
    }
    if (retour.droit() == null)
    {if (true) return null;}
    else
    {if (true) return retour;}
    throw new Error("Missing return statement in function");
  }

  static final public Arbre ou() throws ParseException {
  Arbre fd, retour;
    retour = new Arbre(new Barre());
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OU:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      jj_consume_token(OU);
      fd = comp();
      retour.AjouterFilsDroit(fd);
    }
    if (retour.droit() == null)
    {if (true) return null;}
    else
    {if (true) return retour;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ComportementRobotsTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1f060,0x1f000,0x400,0x800,0x100,0x200,};
   }

  /** Constructor with InputStream. */
  public ComportementRobots(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ComportementRobots(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ComportementRobotsTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ComportementRobots(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ComportementRobotsTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ComportementRobots(ComportementRobotsTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ComportementRobotsTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[17];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 17; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
