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
import org.benf.cfr.reader.state.OsInfo;

/**
 * @author Enaium
 * @since 0.7.0
 */
@SuppressWarnings("unused")
public class CFRConfig extends Config {
    private static final String CFR_WEBSITE = "https://benf.org/other/cfr/";
    public final EnableValue SUGAR_STRINGBUFFER = new EnableValue(
            "stringbuffer", false,
            CFR_WEBSITE + "stringbuilder-vs-concatenation.html");
    public final EnableValue SUGAR_STRINGBUILDER = new EnableValue(
            "stringbuilder", true,
            CFR_WEBSITE + "stringbuilder-vs-concatenation.html");
    public final EnableValue SUGAR_STRINGCONCATFACTORY = new EnableValue(
            "stringconcat", true,
            CFR_WEBSITE + "java9stringconcat.html");
    public final EnableValue ENUM_SWITCH = new EnableValue(
            "decodeenumswitch", true,
            "Re-sugar switch on enum - see " + CFR_WEBSITE + "switch-on-enum.html");
    public final EnableValue ENUM_SUGAR = new EnableValue(
            "sugarenums", true,
            "Re-sugar enums - see " + CFR_WEBSITE + "how-are-enums-implemented.html");
    public final EnableValue STRING_SWITCH = new EnableValue(
            "decodestringswitch", true,
            "Re-sugar switch on String - see " + CFR_WEBSITE + "java7switchonstring.html");
    public final EnableValue PREVIEW_FEATURES = new EnableValue(
            "previewfeatures", true,
            "Decompile preview features if class was compiled with 'javac --enable-preview'");
    public final EnableValue SEALED = new EnableValue(
            "sealed", false,
            "Decompile 'sealed' constructs");
    public final EnableValue SWITCH_EXPRESSION = new EnableValue(
            "switchexpression", false,
            "Re-sugar switch expression");
    public final EnableValue RECORD_TYPES = new EnableValue(
            "recordtypes", false,
            "Re-sugar record types");
    public final EnableValue INSTANCEOF_PATTERN = new EnableValue(
            "instanceofpattern", false,
            "Re-sugar instanceof pattern matches");
    public final EnableValue ARRAY_ITERATOR = new EnableValue(
            "arrayiter", true,
            "Re-sugar array based iteration");
    public final EnableValue COLLECTION_ITERATOR = new EnableValue(
            "collectioniter", true,
            "Re-sugar collection based iteration");
    public final EnableValue REWRITE_TRY_RESOURCES = new EnableValue(
            "tryresources", true,
            "Reconstruct try-with-resources");
    public final EnableValue REWRITE_LAMBDAS = new EnableValue(
            "decodelambdas", true,
            "Re-build lambda functions");
    public final EnableValue DECOMPILE_INNER_CLASSES = new EnableValue(
            "innerclasses", true,
            "Decompile inner classes");
    public final EnableValue FORBID_METHOD_SCOPED_CLASSES = new EnableValue(
            "forbidmethodscopedclasses", false,
            "Don't allow method scoped classes.   Note - this will NOT be used as a fallback, it must be specified.\nIt will produce odd code.");
    public final EnableValue FORBID_ANONYMOUS_CLASSES = new EnableValue(
            "forbidanonymousclasses", false,
            "Don't allow anonymous classes.   Note - this will NOT be used as a fallback, it must be specified.\nIt will produce odd code.");
    public final EnableValue SKIP_BATCH_INNER_CLASSES = new EnableValue(
            "skipbatchinnerclasses", true,
            "When processing many files, skip inner classes, as they will be processed as part of outer classes anyway.  If false, you will see inner classes as separate entities also.");
    public final EnableValue HIDE_UTF8 = new EnableValue(
            "hideutf", true,
            "Hide UTF8 characters - quote them instead of showing the raw characters");
    public final EnableValue HIDE_LONGSTRINGS = new EnableValue(
            "hidelongstrings", false,
            "Hide very long strings - useful if obfuscators have placed fake code in strings");
    public final EnableValue REMOVE_BOILERPLATE = new EnableValue(
            "removeboilerplate", true,
            "Remove boilderplate functions - constructor boilerplate, lambda deserialisation etc.");
    public final EnableValue REMOVE_INNER_CLASS_SYNTHETICS = new EnableValue(
            "removeinnerclasssynthetics", true,
            "Remove (where possible) implicit outer class references in inner classes");
    public final EnableValue RELINK_CONSTANTS = new EnableValue(
            "relinkconst", true,
            "Relink constants - if there is an inlined reference to a field, attempt to de-inline.");
    public final EnableValue RELINK_CONSTANT_STRINGS = new EnableValue(
            "relinkconststring", false,
            "Relink constant strings - if there is a local reference to a string which matches a static final, use the static final.");
    public final EnableValue LIFT_CONSTRUCTOR_INIT = new EnableValue(
            "liftconstructorinit", true,
            "Lift initialisation code common to all constructors into member initialisation");
    public final EnableValue REMOVE_DEAD_METHODS = new EnableValue(
            "removedeadmethods", true,
            "Remove pointless methods - default constructor etc.");
    public final EnableValue REMOVE_BAD_GENERICS = new EnableValue(
            "removebadgenerics", true,
            "Hide generics where we've obviously got it wrong, and fallback to non-generic");
    public final EnableValue SUGAR_ASSERTS = new EnableValue(
            "sugarasserts", true,
            "Re-sugar assert calls");
    public final EnableValue SUGAR_BOXING = new EnableValue(
            "sugarboxing", true,
            "Where possible, remove pointless boxing wrappers");
    public final EnableValue SUGAR_RETRO_LAMBDA = new EnableValue(
            "sugarretrolambda", false,
            "Where possible, resugar uses of retro lambda");
    public final EnableValue SHOW_CFR_VERSION = new EnableValue(
            "showversion", true,
            "Show used CFR version in header (handy to turn off when regression testing)");
    public final EnableValue DECODE_FINALLY = new EnableValue(
            "decodefinally", true,
            "Re-sugar finally statements");
    public final EnableValue TIDY_MONITORS = new EnableValue(
            "tidymonitors", true,
            "Remove support code for monitors - e.g. catch blocks just to exit a monitor");
    public final EnableValue COMMENT_MONITORS = new EnableValue(
            "commentmonitors", false,
            "Replace monitors with comments - useful if we're completely confused");
    public final EnableValue LENIENT = new EnableValue(
            "lenient", false,
            "Be a bit more lenient in situations where we'd normally throw an exception");
    public final EnableValue DUMP_CLASS_PATH = new EnableValue(
            "dumpclasspath", false,
            "Dump class path for debugging purposes");
    public final EnableValue DECOMPILER_COMMENTS = new EnableValue(
            "comments", true,
            "Output comments describing decompiler status, fallback flags etc.");
    public final EnableValue FORCE_TOPSORT = new EnableValue(
            "forcetopsort", false,
            "Force basic block sorting.  Usually not necessary for code emitted directly from javac, but required in the case of obfuscation (or dex2jar!).  Will be enabled in recovery.");
    public final EnableValue FOR_LOOP_CAPTURE = new EnableValue(
            "forloopaggcapture", false,
            "Allow for loops to aggressively roll mutations into update section, even if they don't appear to be involved with the predicate");
    public final EnableValue FORCE_TOPSORT_EXTRA = new EnableValue(
            "forcetopsortaggress", false,
            "Force extra aggressive topsort options");
    public final EnableValue FORCE_TOPSORT_NOPULL = new EnableValue(
            "forcetopsortnopull", false,
            "Force topsort not to pull try blocks");
    public final EnableValue FORCE_COND_PROPAGATE = new EnableValue(
            "forcecondpropagate", false,
            "Pull results of deterministic jumps back through some constant assignments");
    public final EnableValue REDUCE_COND_SCOPE = new EnableValue(
            "reducecondscope", false,
            "Reduce the scope of conditionals, possibly generating more anonymous blocks");
    public final EnableValue FORCE_RETURNING_IFS = new EnableValue(
            "forcereturningifs", false,
            "Move return up to jump site");
    public final EnableValue IGNORE_EXCEPTIONS_ALWAYS = new EnableValue(
            "ignoreexceptionsalways", false,
            "Drop exception information (WARNING : changes semantics, dangerous!)");
    public final EnableValue ANTI_OBF = new EnableValue(
            "antiobf", false,
            "Undo various obfuscations");
    public final EnableValue CONTROL_FLOW_OBF = new EnableValue(
            "obfcontrol", false,
            "Undo control flow obfuscation");
    public final EnableValue ATTRIBUTE_OBF = new EnableValue(
            "obfattr", false,
            "Undo attribute obfuscation");
    public final EnableValue CONST_OBF = new EnableValue(
            "constobf", false,
            "Undo constant obfuscation");
    public final EnableValue HIDE_BRIDGE_METHODS = new EnableValue(
            "hidebridgemethods", true,
            "Hide bridge methods");
    public final EnableValue IGNORE_EXCEPTIONS = new EnableValue(
            "ignoreexceptions", false,
            "Drop exception information if completely stuck (WARNING : changes semantics, dangerous!)");
    public final EnableValue FORCE_PRUNE_EXCEPTIONS = new EnableValue(
            "forceexceptionprune", false,
            "Remove nested exception handlers if they don't change semantics");
    public final EnableValue FORCE_AGGRESSIVE_EXCEPTION_AGG = new EnableValue(
            "aexagg", false,
            "Try to extend and merge exceptions more aggressively");
    public final EnableValue FORCE_AGGRESSIVE_EXCEPTION_AGG2 = new EnableValue(
            "aexagg2", false,
            "Try to extend and merge exceptions more aggressively (may change semantics)");
    public final EnableValue RECOVER_TYPECLASHES = new EnableValue(
            "recovertypeclash", false,
            "Split lifetimes where analysis caused type clash");
    public final EnableValue USE_RECOVERED_ITERATOR_TYPE_HINTS = new EnableValue(
            "recovertypehints", false,
            "Recover type hints for iterators from first pass");
    public final StringValue OUTPUT_DIR = new StringValue(
            "outputdir", null,
            "Decompile to files in [directory] (= options 'outputpath' + 'clobber') (historic compatibility)");
    public final StringValue OUTPUT_PATH = new StringValue(
            "outputpath", null,
            "Decompile to files in [directory]");
    public final StringValue OUTPUT_ENCODING = new StringValue(
            "outputencoding", null,
            "saving decompiled files with specified encoding [encoding]");
    public final EnableValue CLOBBER_FILES = new EnableValue(
            "clobber", false,
            "Overwrite files when using option 'outputpath'");
    public final EnableValue SILENT = new EnableValue(
            "silent", false,
            "Don't display state while decompiling");
    public final EnableValue RECOVER = new EnableValue(
            "recover", true,
            "Allow more and more aggressive options to be set if decompilation fails");
    public final EnableValue ECLIPSE = new EnableValue(
            "eclipse", true,
            "Enable transformations to handle Eclipse code better");
    public final EnableValue OVERRIDES = new EnableValue(
            "override", true,
            "Generate @Override annotations (if method is seen to implement interface method, or override a base class method)");
    public final EnableValue SHOW_INFERRABLE = new EnableValue(
            "showinferrable", false,
            "Decorate methods with explicit types if not implied by arguments");
    public final EnableValue VERSION = new EnableValue(
            "version", true,
            "Show the current CFR version");
    public final StringValue HELP = new StringValue(
            "help", null,
            "Show help for a given parameter");
    public final EnableValue ALLOW_CORRECTING = new EnableValue(
            "allowcorrecting", true,
            "Allow transformations which correct errors, potentially at the cost of altering emitted code behaviour.  An example would be removing impossible (in java!) exception handling - if this has any effect, a warning will be emitted.");
    public final EnableValue LABELLED_BLOCKS = new EnableValue(
            "labelledblocks", true,
            "Allow code to be emitted which uses labelled blocks, (handling odd forward gotos)");
    public final EnableValue JAVA_4_CLASS_OBJECTS = new EnableValue(
            "j14classobj", false,
            "Reverse java 1.4 class object construction");
    public final EnableValue HIDE_LANG_IMPORTS = new EnableValue(
            "hidelangimports", true,
            "Hide imports from java.lang.");
    public final IntegerValue FORCE_PASS = new IntegerValue(
            "recpass", 0,
            "Decompile specifically with recovery options from pass #X. (really only useful for debugging)");
    public final StringValue JAR_FILTER = new StringValue(
            "jarfilter", null,
            "Substring regex - analyse only classes where the fqn matches this pattern. (when analysing jar)");
    public final EnableValue RENAME_MEMBERS = new EnableValue(
            "rename", false,
            "Synonym for 'renamedupmembers' + 'renameillegalidents' + 'renameenumidents'");
    public final EnableValue RENAME_DUP_MEMBERS = new EnableValue(
            "renamedupmembers", false,
            "Rename ambiguous/duplicate fields.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public final IntegerValue RENAME_SMALL_MEMBERS = new IntegerValue(
            "renamesmallmembers", 0,
            "Rename small members.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public final EnableValue RENAME_ILLEGAL_IDENTS = new EnableValue(
            "renameillegalidents", false,
            "Rename identifiers which are not valid java identifiers.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public final EnableValue RENAME_ENUM_MEMBERS = new EnableValue(
            "renameenumidents", false,
            "Rename ENUM identifiers which do not match their 'expected' string names.  Note - this WILL break reflection based access, so is not automatically enabled.");
    public final EnableValue REMOVE_DEAD_CONDITIONALS = new EnableValue(
            "removedeadconditionals", false,
            "Remove code that can't be executed.");
    public final EnableValue AGGRESSIVE_DO_EXTENSION = new EnableValue(
            "aggressivedoextension", false,
            "Fold impossible jumps into do loops with 'first' test");
    public final EnableValue AGGRESSIVE_DUFF = new EnableValue(
            "aggressiveduff", false,
            "Fold duff device style switches with additional control.");
    public final IntegerValue AGGRESSIVE_DO_COPY = new IntegerValue(
            "aggressivedocopy", 0,
            "Clone code from impossible jumps into loops with 'first' test");
    public final IntegerValue AGGRESSIVE_SIZE_REDUCTION_THRESHOLD = new IntegerValue(
            "aggressivesizethreshold", 13000,
            "Opcode count at which to trigger aggressive reductions");
    public final EnableValue STATIC_INIT_RETURN = new EnableValue(
            "staticinitreturn", true,
            "Try to remove return from static init");
    public final EnableValue USE_NAME_TABLE = new EnableValue(
            "usenametable", true,
            "Use local variable name table if present");
    public final StringValue METHODNAME = new StringValue(
            "methodname", null,
            "Name of method to analyse");
    public final StringValue EXTRA_CLASS_PATH = new StringValue(
            "extraclasspath", null,
            "additional class path - classes in this classpath will be used if needed.");
    public final EnableValue PULL_CODE_CASE = new EnableValue(
            "pullcodecase", false,
            "Pull code into case statements agressively");
    public final EnableValue ALLOW_MALFORMED_SWITCH = new EnableValue(
            "allowmalformedswitch", false,
            "Allow potentially malformed switch statements");
    public final EnableValue ELIDE_SCALA = new EnableValue(
            "elidescala", false,
            "Elide things which aren't helpful in scala output (serialVersionUID, @ScalaSignature)");
    public final EnableValue USE_SIGNATURES = new EnableValue(
            "usesignatures", true,
            "Use signatures in addition to descriptors (when they are not obviously incorrect)");
    public final EnableValue CASE_INSENSITIVE_FS_RENAME = new EnableValue(
            "caseinsensitivefs", OsInfo.OS().isCaseInsensitive(),
            "Cope with case insensitive file systems by renaming colliding classes");
    public final EnableValue LOMEM = new EnableValue(
            "lomem", false,
            "Be more agressive about uncaching in order to reduce memory footprint");
    public final StringValue IMPORT_FILTER = new StringValue(
            "importfilter", null,
            "Substring regex - import classes only when fqn matches this pattern. (VNegate with !, eg !lang)");
    public final StringValue OBFUSCATION_PATH = new StringValue(
            "obfuscationpath", null,
            "Path to obfuscation symbol remapping file");
    public final EnableValue TRACK_BYTECODE_LOC = new EnableValue(
            "trackbytecodeloc", false,
            "Propagate bytecode location info.");

    public CFRConfig() {
        super("CFR");
    }
}
