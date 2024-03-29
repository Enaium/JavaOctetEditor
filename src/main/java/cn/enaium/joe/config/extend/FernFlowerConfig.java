/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.config.extend;

import cn.enaium.joe.config.Config;
import cn.enaium.joe.config.value.EnableValue;
import cn.enaium.joe.config.value.IntegerValue;
import cn.enaium.joe.config.value.ModeValue;
import cn.enaium.joe.config.value.StringValue;

import java.util.Arrays;

/**
 * @author Enaium
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class FernFlowerConfig extends Config {
    public EnableValue rbr = new EnableValue("Remove Bridge Methods", true, "Removes any methods that are marked as bridge from the decompiled output.");
    public EnableValue rsy = new EnableValue("Remove Synthetic Methods And Fields", true, "Removes any methods and fields that are marked as synthetic from the decompiled output.");
    public EnableValue din = new EnableValue("Decompile Inner Classes", true, "Process inner classes and add them to the decompiled output.");
    public EnableValue dc4 = new EnableValue("Decompile Java 4 class references", true, "Java 1 to Java 4 had a different class reference format. This resugars them properly.");
    public EnableValue das = new EnableValue("Decompile Assertions", true, "Decompile assert statements.");
    public EnableValue hes = new EnableValue("Hide Empty super()", true, "Hide super() calls with no parameters.");
    public EnableValue hdc = new EnableValue("Hide Default Constructor", true, "Hide constructors with no parameters and no code.");
    public EnableValue dgs = new EnableValue("Decompile Generics", true, "Decompile generics in variables, fields, and statements.");
    public EnableValue ner = new EnableValue("No Exceptions In Return", true, "Integrate returns better in try-catch blocks.");
    public EnableValue esm = new EnableValue("Ensure synchronized ranges are complete", true, "If a synchronized block has a monitorenter without any corresponding monitorexit, try to deduce where one should be to ensure the synchronized is proper.");
    public EnableValue den = new EnableValue("Decompile Enums", true, "Decompile enums.");
    public EnableValue rgn = new EnableValue("Remove reference getClass()", true, "obj.new Inner() or calling invoking a method on a method reference will create a synthetic getClass() call. This removes it.");
    public EnableValue lit = new EnableValue("Keep Literals As Is", false, "Keep NaN, infinties, and pi values as is without resugaring them.");
    public EnableValue bto = new EnableValue("Represent boolean as 0/1", true, "The JVM represents booleans as integers 0 and 1. This decodes 0 and 1 as boolean when it makes sense.");
    public EnableValue asc = new EnableValue("ASCII String Characters", false, "Encode non-ASCII characters in string and character literals as Unicode escapes.");
    public EnableValue nns = new EnableValue("Synthetic Not Set", false, "Treat some known structures as synthetic even when not explicitly set.");
    public EnableValue uto = new EnableValue("Treat Undefined Param Type As Object", true, "Treat nameless types as java.lang.Object.");
    public EnableValue udv = new EnableValue("Use LVT Names", true, "Use LVT names for local variables and parameters instead of var<index>_<version>.");
    public EnableValue ump = new EnableValue("Use Method Parameters", true, "Use method parameter names, as given in the MethodParameters attribute.");
    public EnableValue rer = new EnableValue("Remove Empty try-catch blocks", true, "Remove try-catch blocks with no code.");
    public EnableValue fdi = new EnableValue("Decompile Finally", true, "Decompile finally blocks.");
    public EnableValue inn = new EnableValue("Resugar Intellij IDEA @NotNull", true, "Resugar Intellij IDEA's code generated by @NotNull annotations.");
    public EnableValue lac = new EnableValue("Decompile Lambdas as Anonymous Classes", false, "Decompile lambda expressions as anonymous classes.");
    public EnableValue bsm = new EnableValue("Bytecode to Source Mapping", false, "Map Bytecode to source lines.");
    public EnableValue dcl = new EnableValue("Dump Code Lines", false, "Dump line mappings to output archive zip entry extra data");
    public EnableValue iib = new EnableValue("Ignore Invalid Bytecode", false, "Ignore bytecode that is malformed.");
    public EnableValue vac = new EnableValue("Verify Anonymous Classes", false, "Verify that anonymous classes are local.");
    public EnableValue tcs = new EnableValue("Ternary Constant Simplification", false, "Fold branches of ternary expressions that have boolean true and false constants.");
    public EnableValue pam = new EnableValue("Pattern Matching", true, "Decompile with if and switch pattern matching enabled.");
    public EnableValue tlf = new EnableValue("[Experimental] Try-Loop fix", false, "Code with a while loop inside of a try-catch block sometimes is malformed. This attempts to fix it, but may cause other issues.");
    public EnableValue tco = new EnableValue("[Experimental] Ternary In If Conditions", false, "Tries to collapse if statements that have a ternary in their condition.");
    public EnableValue swe = new EnableValue("Decompile Switch Expressions", true, "Decompile switch expressions in modern Java class files.");
    public EnableValue shs = new EnableValue("[Debug] Show hidden statements", false, "Display code blocks hidden, for debugging purposes");
    public EnableValue ovr = new EnableValue("Override Annotation", true, "Display override annotations for methods known to the decompiler.");
    public EnableValue ssp = new EnableValue("Second-Pass Stack Simplficiation", true, "Simplify variables across stack bounds to resugar complex statements.");
    public EnableValue iec = new EnableValue("Include Entire Classpath", false, "Give the decompiler information about every jar on the classpath.");
    public EnableValue jrt = new EnableValue("Include Java Runtime", false, "Give the decompiler information about the Java runtime.");
    public EnableValue ega = new EnableValue("Explicit Generic Arguments", false, "Put explicit diamond generic arguments on method calls.");
    public EnableValue isl = new EnableValue("Inline Simple Lambdas", true, "Remove braces on simple, one line, lambda expressions.");
    public ModeValue log = new ModeValue("Logging Level", "INFO", "Logging level. Must be one of: 'info', 'debug', 'warn', 'error'.", Arrays.asList("INFO", "DEBUG", "WARN", "ERROR"));
    public EnableValue mpm = new EnableValue("[DEPRECATED] Max time to process method", false, "Maximum time in seconds to process a method. This is deprecated, do not use.");
    public EnableValue ren = new EnableValue("Rename Members", false, "Rename classes, fields, and methods with a number suffix to help in deobfuscation.");
    public StringValue urc = new StringValue("User Renamer Class", "", "Path to a class that implements IIdentifierRenamer.");
    public EnableValue nls = new EnableValue("New Line Seperator", false, "Character that seperates lines in the decompiled output.");
    public StringValue ind = new StringValue("Indent String", "   ", "A string of spaces or tabs that is placed for each indent level.");
    public IntegerValue pll = new IntegerValue("Preferred line length", 160, "Max line length before formatting is applied.");
    public StringValue ban = new StringValue("User Renamer Class", "", "Path to a class that implements IIdentifierRenamer.");
    public StringValue erm = new StringValue("Error Message", "Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)", "Message to display when an error occurs in the decompiler.");
    public IntegerValue thr = new IntegerValue("Thread Count", 16, "How many threads to use to decompile.");
    public EnableValue jvn = new EnableValue("JAD-Style Variable Naming", false, "Use JAD-style variable naming for local variables, instead of var<index>_<version>A.");
    public EnableValue dbe = new EnableValue("Dump Bytecode On Error", true, "Put the bytecode in the method body when an error occurs.");
    public EnableValue dee = new EnableValue("Dump Exceptions On Error", true, "Put the exception message in the method body when an error occurs.");
    public EnableValue dec = new EnableValue("Decompiler Comments", true, "Sometimes, odd behavior of the bytecode or unfixable problems occur. This enables or disables the adding of those to the decompiled output.");


    public FernFlowerConfig() {
        super("FernFlower");
    }
}
