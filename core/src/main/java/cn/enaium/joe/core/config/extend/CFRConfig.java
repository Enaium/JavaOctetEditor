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

package cn.enaium.joe.core.config.extend;

import cn.enaium.joe.core.config.Config;
import cn.enaium.joe.core.config.value.EnableValue;
import cn.enaium.joe.core.config.value.IntegerValue;
import cn.enaium.joe.core.config.value.StringValue;
import org.benf.cfr.reader.state.OsInfo;

/**
 * @author Enaium
 * @since 2.0.0
 */
@SuppressWarnings("unused")
public class CFRConfig extends Config {
    private static final String CFR_WEBSITE = "https://benf.org/other/cfr/";
    public EnableValue sugarStringbuffer = new EnableValue(
            "stringbuffer", false,
            CFR_WEBSITE + "stringbuilder-vs-concatenation.html");
    public EnableValue sugarStringbuilder = new EnableValue(
            "stringbuilder", true,
            CFR_WEBSITE + "stringbuilder-vs-concatenation.html");
    public EnableValue sugarStringconcatfactory = new EnableValue(
            "stringconcat", true,
            CFR_WEBSITE + "java9stringconcat.html");
    public EnableValue enumSwitch = new EnableValue(
            "decodeenumswitch", true,
            "Re-sugar switch on enum - see " + CFR_WEBSITE + "switch-on-enum.html");
    public EnableValue enumSugar = new EnableValue(
            "sugarenums", true,
            "Re-sugar enums - see " + CFR_WEBSITE + "how-are-enums-implemented.html");
    public EnableValue stringSwitch = new EnableValue(
            "decodestringswitch", true,
            "Re-sugar switch on String - see " + CFR_WEBSITE + "java7switchonstring.html");
    public EnableValue previewFeatures = new EnableValue(
            "previewfeatures", true,
            "Decompile preview features if class was compiled with 'javac --enable-preview'");
    public EnableValue sealed = new EnableValue(
            "sealed", false,
            "Decompile 'sealed' constructs");
    public EnableValue switchExpression = new EnableValue(
            "switchexpression", false,
            "Re-sugar switch expression");
    public EnableValue recordTypes = new EnableValue(
            "recordtypes", false,
            "Re-sugar record types");
    public EnableValue instanceofPattern = new EnableValue(
            "instanceofpattern", false,
            "Re-sugar instanceof pattern matches");
    public EnableValue arrayIterator = new EnableValue(
            "arrayiter", true,
            "Re-sugar array based iteration");
    public EnableValue collectionIterator = new EnableValue(
            "collectioniter", true,
            "Re-sugar collection based iteration");
    public EnableValue rewriteTryResources = new EnableValue(
            "tryresources", true,
            "Reconstruct try-with-resources");
    public EnableValue rewriteLambdas = new EnableValue(
            "decodelambdas", true,
            "Re-build lambda functions");
    public EnableValue decompileInnerClasses = new EnableValue(
            "innerclasses", true,
            "Decompile inner classes");
    public EnableValue forbidMethodScopedClasses = new EnableValue(
            "forbidmethodscopedclasses", false,
            "Don't allow method scoped classes.   Note - this will NOT be used as a fallback, it must be specified.\nIt will produce odd code.");
    public EnableValue forbidAnonymousClasses = new EnableValue(
            "forbidanonymousclasses", false,
            "Don't allow anonymous classes.   Note - this will NOT be used as a fallback, it must be specified.\nIt will produce odd code.");
    public EnableValue skipBatchInnerClasses = new EnableValue(
            "skipbatchinnerclasses", true,
            "When processing many files, skip inner classes, as they will be processed as part of outer classes anyway.  If false, you will see inner classes as separate entities also.");
    public EnableValue hideUtf8 = new EnableValue(
            "hideutf", true,
            "Hide UTF8 characters - quote them instead of showing the raw characters");
    public EnableValue hideLongstrings = new EnableValue(
            "hidelongstrings", false,
            "Hide very long strings - useful if obfuscators have placed fake code in strings");
    public EnableValue removeBoilerplate = new EnableValue(
            "removeboilerplate", true,
            "Remove boilderplate functions - constructor boilerplate, lambda deserialisation etc.");
    public EnableValue removeInnerClassSynthetics = new EnableValue(
            "removeinnerclasssynthetics", true,
            "Remove (where possible) implicit outer class references in inner classes");
    public EnableValue relinkConstants = new EnableValue(
            "relinkconst", true,
            "Relink constants - if there is an inlined reference to a field, attempt to de-inline.");
    public EnableValue relinkConstantStrings = new EnableValue(
            "relinkconststring", false,
            "Relink constant strings - if there is a local reference to a string which matches a static final, use the static final.");
    public EnableValue liftConstructorInit = new EnableValue(
            "liftconstructorinit", true,
            "Lift initialisation code common to all constructors into member initialisation");
    public EnableValue removeDeadMethods = new EnableValue(
            "removedeadmethods", true,
            "Remove pointless methods - default constructor etc.");
    public EnableValue removeBadGenerics = new EnableValue(
            "removebadgenerics", true,
            "Hide generics where we've obviously got it wrong, and fallback to non-generic");
    public EnableValue sugarAsserts = new EnableValue(
            "sugarasserts", true,
            "Re-sugar assert calls");
    public EnableValue sugarBoxing = new EnableValue(
            "sugarboxing", true,
            "Where possible, remove pointless boxing wrappers");
    public EnableValue sugarRetroLambda = new EnableValue(
            "sugarretrolambda", false,
            "Where possible, resugar uses of retro lambda");
    public EnableValue showCfrVersion = new EnableValue(
            "showversion", true,
            "Show used CFR version in header (handy to turn off when regression testing)");
    public EnableValue decodeFinally = new EnableValue(
            "decodefinally", true,
            "Re-sugar finally statements");
    public EnableValue tidyMonitors = new EnableValue(
            "tidymonitors", true,
            "Remove support code for monitors - e.g. catch blocks just to exit a monitor");
    public EnableValue commentMonitors = new EnableValue(
            "commentmonitors", false,
            "Replace monitors with comments - useful if we're completely confused");
    public EnableValue lenient = new EnableValue(
            "lenient", false,
            "Be a bit more lenient in situations where we'd normally throw an exception");
    public EnableValue dumpClassPath = new EnableValue(
            "dumpclasspath", false,
            "Dump class path for debugging purposes");
    public EnableValue decompilerComments = new EnableValue(
            "comments", true,
            "Output comments describing decompiler status, fallback flags etc.");
    public EnableValue forceTopsort = new EnableValue(
            "forcetopsort", false,
            "Force basic block sorting.  Usually not necessary for code emitted directly from javac, but required in the case of obfuscation (or dex2jar!).  Will be enabled in recovery.");
    public EnableValue forLoopCapture = new EnableValue(
            "forloopaggcapture", false,
            "Allow for loops to aggressively roll mutations into update section, even if they don't appear to be involved with the predicate");
    public EnableValue forceTopsortExtra = new EnableValue(
            "forcetopsortaggress", false,
            "Force extra aggressive topsort options");
    public EnableValue forceTopsortNopull = new EnableValue(
            "forcetopsortnopull", false,
            "Force topsort not to pull try blocks");
    public EnableValue forceCondPropagate = new EnableValue(
            "forcecondpropagate", false,
            "Pull results of deterministic jumps back through some constant assignments");
    public EnableValue reduceCondScope = new EnableValue(
            "reducecondscope", false,
            "Reduce the scope of conditionals, possibly generating more anonymous blocks");
    public EnableValue forceReturningIfs = new EnableValue(
            "forcereturningifs", false,
            "Move return up to jump site");
    public EnableValue ignoreExceptionsAlways = new EnableValue(
            "ignoreexceptionsalways", false,
            "Drop exception information (WARNING : changes semantics, dangerous!)");
    public EnableValue antiObf = new EnableValue(
            "antiobf", false,
            "Undo various obfuscations");
    public EnableValue controlFlowObf = new EnableValue(
            "obfcontrol", false,
            "Undo control flow obfuscation");
    public EnableValue attributeObf = new EnableValue(
            "obfattr", false,
            "Undo attribute obfuscation");
    public EnableValue constObf = new EnableValue(
            "constobf", false,
            "Undo constant obfuscation");
    public EnableValue hideBridgeMethods = new EnableValue(
            "hidebridgemethods", true,
            "Hide bridge methods");
    public EnableValue ignoreExceptions = new EnableValue(
            "ignoreexceptions", false,
            "Drop exception information if completely stuck (WARNING : changes semantics, dangerous!)");
    public EnableValue forcePruneExceptions = new EnableValue(
            "forceexceptionprune", false,
            "Remove nested exception handlers if they don't change semantics");
    public EnableValue forceAggressiveExceptionAgg = new EnableValue(
            "aexagg", false,
            "Try to extend and merge exceptions more aggressively");
    public EnableValue forceAggressiveExceptionAgg2 = new EnableValue(
            "aexagg2", false,
            "Try to extend and merge exceptions more aggressively (may change semantics)");
    public EnableValue recoverTypeclashes = new EnableValue(
            "recovertypeclash", false,
            "Split lifetimes where analysis caused type clash");
    public EnableValue useRecoveredIteratorTypeHints = new EnableValue(
            "recovertypehints", false,
            "Recover type hints for iterators from first pass");
    public StringValue outputDir = new StringValue(
            "outputdir", null,
            "Decompile to files in [directory] (= options 'outputpath' + 'clobber') (historic compatibility)");
    public StringValue outputPath = new StringValue(
            "outputpath", null,
            "Decompile to files in [directory]");
    public StringValue outputEncoding = new StringValue(
            "outputencoding", null,
            "saving decompiled files with specified encoding [encoding]");
    public EnableValue clobberFiles = new EnableValue(
            "clobber", false,
            "Overwrite files when using option 'outputpath'");
    public EnableValue silent = new EnableValue(
            "silent", false,
            "Don't display state while decompiling");
    public EnableValue recover = new EnableValue(
            "recover", true,
            "Allow more and more aggressive options to be set if decompilation fails");
    public EnableValue eclipse = new EnableValue(
            "eclipse", true,
            "Enable transformations to handle Eclipse code better");
    public EnableValue overrides = new EnableValue(
            "override", true,
            "Generate @Override annotations (if method is seen to implement interface method, or override a base class method)");
    public EnableValue showInferrable = new EnableValue(
            "showinferrable", false,
            "Decorate methods with explicit types if not implied by arguments");
    public EnableValue version = new EnableValue(
            "version", true,
            "Show the current CFR version");
    public StringValue help = new StringValue(
            "help", null,
            "Show help for a given parameter");
    public EnableValue allowCorrecting = new EnableValue(
            "allowcorrecting", true,
            "Allow transformations which correct errors, potentially at the cost of altering emitted code behaviour.  An example would be removing impossible (in java!) exception handling - if this has any effect, a warning will be emitted.");
    public EnableValue labelledBlocks = new EnableValue(
            "labelledblocks", true,
            "Allow code to be emitted which uses labelled blocks, (handling odd forward gotos)");
    public EnableValue java4ClassObjects = new EnableValue(
            "j14classobj", false,
            "Reverse java 1.4 class object construction");
    public EnableValue hideLangImports = new EnableValue(
            "hidelangimports", true,
            "Hide imports from java.lang.");
    public IntegerValue forcePass = new IntegerValue(
            "recpass", 0,
            "Decompile specifically with recovery options from pass #X. (really only useful for debugging)");
    public StringValue jarFilter = new StringValue(
            "jarfilter", null,
            "Substring regex - analyse only classes where the fqn matches this pattern. (when analysing jar)");
    public EnableValue renameMembers = new EnableValue(
            "rename", false,
            "Synonym for 'renamedupmembers' + 'renameillegalidents' + 'renameenumidents'");
    public EnableValue renameDupMembers = new EnableValue(
            "renamedupmembers", false,
            "Rename ambiguous/duplicate fields.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public IntegerValue renameSmallMembers = new IntegerValue(
            "renamesmallmembers", 0,
            "Rename small members.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public EnableValue renameIllegalIdents = new EnableValue(
            "renameillegalidents", false,
            "Rename identifiers which are not valid java identifiers.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public EnableValue renameEnumMembers = new EnableValue(
            "renameenumidents", false,
            "Rename ENUM identifiers which do not match their 'expected' string names.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public EnableValue removeDeadConditionals = new EnableValue(
            "removedeadconditionals", false,
            "Remove code that can't be executed.");
    public EnableValue aggressiveDoExtension = new EnableValue(
            "aggressivedoextension", false,
            "Fold impossible jumps into do loops with 'first' test");
    public EnableValue aggressiveDuff = new EnableValue(
            "aggressiveduff", false,
            "Fold duff device style switches with additional control.");
    public IntegerValue aggressiveDoCopy = new IntegerValue(
            "aggressivedocopy", 0,
            "Clone code from impossible jumps into loops with 'first' test");
    public IntegerValue aggressiveSizeReductionThreshold = new IntegerValue(
            "aggressivesizethreshold", 13000,
            "Opcode count at which to trigger aggressive reductions");
    public EnableValue staticInitReturn = new EnableValue(
            "staticinitreturn", true,
            "Try to remove return from static init");
    public EnableValue useNameTable = new EnableValue(
            "usenametable", true,
            "Use local variable name table if present");
    public StringValue methodname = new StringValue(
            "methodname", null,
            "Name of method to analyse");
    public StringValue extraClassPath = new StringValue(
            "extraclasspath", null,
            "additional class path - classes in this classpath will be used if needed.");
    public EnableValue pullCodeCase = new EnableValue(
            "pullcodecase", false,
            "Pull code into case statements agressively");
    public EnableValue allowMalformedSwitch = new EnableValue(
            "allowmalformedswitch", false,
            "Allow potentially malformed switch statements");
    public EnableValue elideScala = new EnableValue(
            "elidescala", false,
            "Elide things which aren't helpful in scala output (serialVersionUID, @ScalaSignature)");
    public EnableValue useSignatures = new EnableValue(
            "usesignatures", true,
            "Use signatures in addition to descriptors (when they are not obviously incorrect)");
    public EnableValue caseInsensitiveFsRename = new EnableValue(
            "caseinsensitivefs", OsInfo.OS().isCaseInsensitive(),
            "Cope with case insensitive file systems by renaming colliding classes");
    public EnableValue lomem = new EnableValue(
            "lomem", false,
            "Be more agressive about uncaching in order to reduce memory footprint");
    public StringValue importFilter = new StringValue(
            "importfilter", null,
            "Substring regex - import classes only when fqn matches this pattern. (VNegate with !, eg !lang)");
    public StringValue obfuscationPath = new StringValue(
            "obfuscationpath", null,
            "Path to obfuscation symbol remapping file");
    public EnableValue trackBytecodeLoc = new EnableValue(
            "trackbytecodeloc", false,
            "Propagate bytecode location info.");

    public CFRConfig() {
        super("CFR");
    }
}
