/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 Nerd4j
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.nerd4j.csv;


/** 
 * Enumerates all the ASCII characters that are remarkable
 * for parsing and formatting purposes.
 * 
 * @author Nerd4j Team
 */
public interface RemarkableASCII
{
	
    /* ************************** */
    /* *** NOTABLE CHARACTERS *** */
    /* ************************** */
    
    /*
     * For Unicode line terminators see:
     * http://en.wikipedia.org/wiki/Newline#Unicode
     * http://www.unicode.org/standard/reports/tr13/tr13-5.html
     * 
     * Well known new line sequences are:
     * CR:      Commodore 8-bit, BBC Acorn, TRS-80, Apple II, Mac OS <=v9, OS-9
     * CR+LF:   Windows, TOPS-10, RT-11, CP/M, MP/M, DOS, Atari TOS, OS/2, Symbian OS, Palm OS
     * LF:      Multics, Unix, Unix-like, BeOS, Amiga, RISC OS
     * LF+CR:   BBC Acorn, RISC OS spooled text output.
     * RS:      QNX (pre-POSIX)
     */
    
    
    /* Record separators (new line). */
    
    /** Line Feed, 0x000A, Unicode standard RS, Control Key: ^J */
    public static final char LF                 = 0x000A;
    
    /** Vertical Tab, 0x000B, Unicode standard RS, Control Key: ^K */
    public static final char VT                 = 0x000B;
    
    /** Form Feed, 0x000C, Unicode standard RS, Control Key: ^L */
    public static final char FF                 = 0x000C;
    
    /** Carriage Return, 0x000D, Unicode standard RS, Control Key: ^M */
    public static final char CR                 = 0x000D;
    
    /** Record Separator, 0x001E, Unicode standard RS, Control Key: ^^ */
    public static final char RS                 = 0x001E;
    
    
    /* Record separators (new line) outside ASCII space. */
    
    /** New Line, 0x0085, Unicode standard RS */
    public static final char NEL                = 0x0085;
    
    /** Line Separator, 0x2028, Unicode standard RS */
    public static final char LS                 = 0x2028;
    
    /** Paragraph Separator, 0x2029, Unicode standard RS */
    public static final char PS                 = 0x2029;
    
    
    /* Common field terminators. */
    
    /** Comma, 0x002C, ',' */
    public static final char COMMA              = 0x002C;
    
    /** Full Stop, 0x002E, '.' */
    public static final char FULL_STOP          = 0x002E;
    
    /** Semicolon, 0x003B, ';' */
    public static final char SEMICOLON          = 0x003B;
    
    /** Colon, 0x003A, ':' */
    public static final char COLON              = 0x003A;
    
    /** Pipe, 0x007C, '|' */
    public static final char PIPE               = 0x007C;
    
    /** Space, 0x0020, ' ' */
    public static final char SPACE              = 0x0020;
    
    /** Horizontal Tab, 0x0009, '\t', Control Key: ^I */
    public static final char HT                 = 0x0009;
    
    
    /* Common quotes. */
    
    /** Single Quote, 0x0027, ''' */
    public static final char SINGLE_QUOTE       = 0x0027;
    
    /** Double Quote, 0x0022, '"' */
    public static final char DOUBLE_QUOTE       = 0x0022;
    
    /* Common escapes. */
    
    /** Backslash, 0x005C, '\' */
    public static final char BACKSLASH          = 0x005C;
    
    /** Esc, 0x001B, Control Key: ^[ */
    public static final char ESC                = 0x001B;
    
    /** Caret, 0x005E, '^' */
    public static final char CARET              = 0x005E;
    
    
    /* NULL value in ASCII code. */
    
    /** Null, 0x0000, '' */
    public static final char NULL               = 0x0000;

    
    /*
     * NOT_AN_ASCII is not a valid ASCII code.
     * This value is used to represent an empty
     * ASCII value. 
     */
    
    /** No ASCII char, 0xFFFF, -1 */
    public static final char NOT_AN_ASCII       = 0xFFFF;
    
    
    /**
     * ASCII space size, here just to simplify code reading: first character
     * 0x00, last character 0x7F.
     */
    public static final int ASCII_TABLE_SIZE    = 0x80;
	
}
