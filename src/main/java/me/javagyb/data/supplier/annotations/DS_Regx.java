package me.javagyb.data.supplier.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Created by javagyb on 2018/1/29.
 *
 *     Limitations
 *     -----------
 *
 *     this does not support all valid Java regular expressions. The full
 *     set of what is defined here and is summarized below. Future versions
 *     might support a more complete set, in case of popular demand.
 *
 *     regexp  ::=     unionexp
 *     |
 *     unionexp        ::=     interexp | unionexp     (union)
 *     |       interexp
 *     interexp        ::=     concatexp & interexp    (intersection)  [OPTIONAL]
 *     |       concatexp
 *     concatexp       ::=     repeatexp concatexp     (concatenation)
 *     |       repeatexp
 *     repeatexp       ::=     repeatexp ?     (zero or one occurrence)
 *     |       repeatexp *     (zero or more occurrences)
 *     |       repeatexp +     (one or more occurrences)
 *     |       repeatexp {n}   (n occurrences)
 *     |       repeatexp {n,}  (n or more occurrences)
 *     |       repeatexp {n,m} (n to m occurrences, including both)
 *     |       complexp
 *     complexp        ::=     ~ complexp      (complement)    [OPTIONAL]
 *     |       charclassexp
 *     charclassexp    ::=     [ charclasses ] (character class)
 *     |       [^ charclasses ]        (negated character class)
 *     |       simpleexp
 *     charclasses     ::=     charclass charclasses
 *     |       charclass
 *     charclass       ::=     charexp - charexp       (character range, including end-points)
 *     |       charexp
 *     simpleexp       ::=     charexp
 *     |       .       (any single character)
 *     |       #       (the empty language)    [OPTIONAL]
 *     |       @       (any string)    [OPTIONAL]
 *     |       " <Unicode string without double-quotes> "      (a string)
 *     |       ( )     (the empty string)
 *     |       ( unionexp )    (precedence override)
 *     |       < <identifier> >        (named automaton)       [OPTIONAL]
 *     |       <n-m>   (numerical interval)    [OPTIONAL]
 *     charexp ::=     <Unicode character>     (a single non-reserved character)
 *     |       \ <Unicode character>   (a single character)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DS_Regx {
    String value();
}
