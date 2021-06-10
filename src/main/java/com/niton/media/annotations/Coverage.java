package com.niton.media.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shows how much code-coverage the tescases yield on this class
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Coverage {
	/**
	 * How many % (from 0 to 1) of all methods are tested
	 * @return a value between 0 and one represening precent
	 */
	double methods();
	/**
	 * How many % (from 0 to 1) of all lines in this file are tested
	 * @return a value between 0 and one representing precent
	 */
	double lines();
}
