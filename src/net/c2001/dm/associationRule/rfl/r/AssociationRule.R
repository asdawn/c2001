# Load, save, view, select association rules
# 
# Author: Lin Dong
###############################################################################

#初始化rJava，启动JVM，添加类库
init = function() {
	library(rJava);
	.jinit();
	.jaddClassPath('d:/c2001.jar');
}

#从文件读取关联规则
arload<-function(filename) {
	file = .jnew("java/io/File",filename);
	rule = .jnew("net/c2001/dm/associationRule/apriori/base/AssociationRule");
	rule = .jcall(rule,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;","loadFromFile",file);
	rule;
}

#保存关联规则至文件
arsave<-function(rule, path) {
	file = .jnew("java/io/File", path);
	.jcall(rule,"Z","saveToFile", file);
}

#查看关联规则
arview<-function(rule) {
	viewer = .jnew("net/c2001/ui/ruleViewer/RuleViewer", .jnull("java/awt/Frame"), rule)
	.jcall(viewer,,"setVisible",TRUE);
}

#查看频繁项集
arviewitemset<-function(rule) {
	viewer = .jnew("net/c2001/ui/ruleViewer/ItemsetViewer", .jnull("java/awt/Frame"), rule);
	.jcall(viewer,,"setVisible",TRUE);
}

#查看关联规则（带评价指标）
arview2<-function(rule, ...) {
	c = c(...)
	len = length(c)
	args = list(...);
	if(len == 0) {
		arview(rule)}
	else {
		n = length(args)/2;
		nvs = vector('list', n);
		for (i in (1 : n)) {
			value = args[[2*i-1]];
			nvs[i] = .jnew("net/c2001/ui/ruleViewer/ValueAndName", .jfloat(value), args[[2*i]]);
		}
		univiewer = .jnew("net/c2001/ui/ruleViewer/UniViewer", .jnull("java/awt/Frame"), rule, .jarray(nvs, 'net/c2001/ui/ruleViewer/ValueAndName'));
		.jcall(univiewer,,"setVisible",TRUE);
	}
}

#获取前n条规则
arfirst<-function(rule, n) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getFirstNRules",rule, n);
	newRule;
}

#获取关联规则筛选器（JVM中唯一实例）
getSelector<-function() {
	selector = .jcall("net/c2001/dm/associationRule/rfl/base/MemRuleSelector","Lnet/c2001/dm/associationRule/rfl/base/MemRuleSelector;","getInstance");
	selector;
}

#获取后n条关联规则
arlast<-function(rule, n) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getLastNRules",rule, n);
	newRule;
}

#支持度低于指定值的规则
arsupl<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesSupLowerThan",rule, .jfloat(support));
	newRule;
}

#支持度小于等于指定值的规则
arsuple<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesSupLowerThanOrEqualTo",rule, .jfloat(support));
	newRule;
}

#支持度大于指定值得的则
arsupg<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesSupGreaterThan",rule, .jfloat(support));
	newRule;
}

#支持度大于等于指定值的规则
arsupge<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesSupGreaterThanOrEqualTo",rule, .jfloat(support));
	newRule;
}

#支持度介于lb与ub之间的规则,ube和ube为TRUE或者FALSE，代表区间闭开
arsupbetween<-function(rule, lb, lbe, ub, ube) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesSupBetween",rule, .jfloat(lb), lbe, .jfloat(ub), ube);
	newRule;
}

#置信度低于指定值的规则
arconfl<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesConfLowerThan",rule, .jfloat(support));
	newRule;
}

#置信度小于等于指定值的规则
arconfle<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesConfLowerThanOrEqualTo",rule, .jfloat(support));
	newRule;
}

#置信度大于指定值得的则
arconfg<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesConfGreaterThan",rule, .jfloat(support));
	newRule;
}

#置信度大于等于指定值的规则
arconfge<-function(rule, support) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesConfGreaterThanOrEqualTo",rule, .jfloat(support));
	newRule;
}

#置信度介于lb与ub之间的规则,ube和ube为TRUE或者FALSE，代表区间闭开
arconfbetween<-function(rule, lb, lbe, ub, ube) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesConfBetween",rule, .jfloat(lb), lbe, .jfloat(ub), ube);
	newRule;
}

#获取指定下标范围内的规则（从0开始，均为包含）
arat<-function(rule, start, end) {
	selector = getSelector();
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "getRulesByIndex",rule, start, end);
	newRule;
}

#对规则进行排序，排序方法可通过arlistsortmethods查询
arsort<-function(rule, method) {
	selector = getSelector();
	methods = J('net.c2001.dm.associationRule.rfl.base.RuleSelector$SortBy');
	m = switch(tolower(method),
			supdesc = methods$SupDesc, 
			supinc = methods$SupInc,
			confdesc = methods$ConfDesc,
			confinc = methods$ConfInc,         
			itemcountdesc = methods$ItemCountDesc,   
			itemcountinc = methods$ItemCountInc,    
			srcitemcountdesc = methods$SrcItemCountDesc,
			srcitemcountinc = methods$SrcItemCountInc,
			dstitemcountdesc = methods$DstItemCountDesc,
			dstitemcountinc = methods$DstItemCountInc, 
			srcdesc = methods$SrcDesc,         
			srcinc = methods$SrcInc,          
			dstdesc = methods$DstDesc,         
			dstinc = methods$DstInc);
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "sortRules", rule, m);
	newRule;
}

#显示支持的排序方法
arlistsortmethods<-function() {
	methods = c("SupDesc",
			"SupInc",
			"ConfDesc",
			"ConfInc",         
			"ItemCountDesc",   
			"ItemCountInc",    
			"SrcItemCountDesc",
			"SrcItemCountInc",
			"DstItemCountDesc",
			"DstItemCountInc", 
			"SrcDesc",         
			"SrcInc",          
			"DstDesc",         
			"DstInc"); 
	message("Sort methods:");
	for (method in methods) {
		message(method);
	}
}

#对规则进行排序，排序方法可通过arlistsortmethods2查询（根据评价指标）
arsort2<-function(rule, floatArray, method) {
	selector = getSelector();
	methods = J('net.c2001.dm.associationRule.rfl.base.RuleSelector$SortBy');
	m = switch(tolower(method),
			floatdesc = methods$FloatDesc, 
			floatinc = methods$FloatInc);
	newRule = .jcall(selector,"Lnet/c2001/dm/associationRule/apriori/base/AssociationRule;", "sortRules", rule, .jfloat(floatArray), m);
	newRule;
}

#显示支持的排序方法(评价指标)
arlistsortmethods2<-function() {
	methods = c("FloatDesc",         
			"FloatInc"); 
	message("Sort methods:");
	for (method in methods) {
		message(method);
	}
}

#后件为指定项集的规则(若为项集的Collection则要求后件包含于此Collection中)
ardstis<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesConsequenceIs(rule, itemset);
	newRule;
}

#后件不为指定项集的规则(若为项集的Collection则要求后件不包含于此Collection中)
ardstisnot<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesConsequenceIsNot(rule, itemset);
	newRule;
}

#前件为指定项集的规则(若为项集的Collection则要求前件包含于此Collection中)
arsrcis<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesAntecedentIs(rule, itemset);
	newRule;
}

#前件不为指定项集的规则(若为项集的Collection则要求前件不包含于此Collection中)
arsrcisnot<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesAntecedentIsNot(rule, itemset);
	newRule;
}

#前件包含指定项集的规则(若为项集的Collection则要求前件包含此Collection中某一项集)
arinsrc<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesInAntecedent(rule, itemset);
	newRule;
}

#后件包含指定项集的规则(若为项集的Collection则要求后件包含此Collection中某一项集)
arindst<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesInConsequence(rule, itemset);
	newRule;
}

#前件不包含指定项集的规则(若为项集的Collection则要求前件不包含此Collection中任意项集)
arnotinsrc<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesNotInAntecedent(rule, itemset);
	newRule;
}

#后件不包含指定项集的规则(若为项集的Collection则要求后件不包含此Collection中任意项集)
arnotindst<-function(rule, itemset) {
	selector = getSelector();
	newRule = selector$getRulesNotInConsequence(rule, itemset);
	newRule;
}

#包含项数为n的关联规则
aritemcount<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesByItemsetCount(rule, n);
	newRule;
}

#包含项数介于start和end之间（含）的关联规则
aritemcountbetween<-function(rule, start, end) {
	selector = getSelector();
	newRule = selector$getRulesByItemsetCount(rule, start, end);
	newRule;
}

#包含项数小于n的关联规则
aritemcountl<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesItemsetCountLessThan(rule, n);
	newRule;
}

#包含项数小于等于n的关联规则
aritemcountle<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesItemsetCountLessThanOrEqualTo(rule, n);
	newRule;
}

#包含项数大于n的关联规则
aritemcountg<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesItemsetCountGreaterThan(rule, n);
	newRule;
}

#包含项数大于等于n的关联规则
aritemcountge<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesItemsetCountGreaterThanOrEqualTo(rule, n);
	newRule;
}

#后件包含项数为n的关联规则
ardstitemcount<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesByDstItemsetCount(rule, n);
	newRule;
}

#后件包含项数介于start和end之间（含）的关联规则
ardstitemcountbetween<-function(rule, start, end) {
	selector = getSelector();
	newRule = selector$getRulesByDstItemsetCount(rule, start, end);
	newRule;
}

#后件包含项数小于n的关联规则
ardstitemcountl<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesDstItemsetCountLessThan(rule, n);
	newRule;
}

#后件包含项数小于等于n的关联规则
ardstitemcountle<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesDstItemsetCountLessThanOrEqualTo(rule, n);
	newRule;
}

#后件包含项数大于n的关联规则
ardstitemcountg<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesDstItemsetCountGreaterThan(rule, n);
	newRule;
}

#后件包含项数大于等于n的关联规则
ardstitemcountge<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesDstItemsetCountGreaterThanOrEqualTo(rule, n);
	newRule;
}

#前件包含项数为n的关联规则
arsrcitemcount<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesBySrcItemsetCount(rule, n);
	newRule;
}

#前件包含项数介于start和end之间（含）的关联规则
arsrcitemcountbetween<-function(rule, start, end) {
	selector = getSelector();
	newRule = selector$getRulesBySrcItemsetCount(rule, start, end);
	newRule;
}

#前件包含项数小于n的关联规则
arsrcitemcountl<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesSrcItemsetCountLessThan(rule, n);
	newRule;
}

#前件包含项数小于等于n的关联规则
arsrcitemcountle<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesSrcItemsetCountLessThanOrEqualTo(rule, n);
	newRule;
}

#前件包含项数大于n的关联规则
arsrcitemcountg<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesSrcItemsetCountGreaterThan(rule, n);
	newRule;
}

#前件包含项数大于等于n的关联规则
arsrcitemcountge<-function(rule, n) {
	selector = getSelector();
	newRule = selector$getRulesSrcItemsetCountGreaterThanOrEqualTo(rule, n);
	newRule;
}

#创建一个itemset
itemset<-function(items) {
	iset = .jnew("net/c2001/dm/associationRule/apriori/base/ItemSet");
	for (item in items) {
		.jcall(iset, , "addField", .jshort(item));
	}
	iset;
}

#创建itemset的Vector
itemsets<-function(isets) {
	vector = .jnew("java.util.Vector");
	for (iset in isets) {
		vector$add(itemset(iset));
	}
	vector;
}

#计算提升度，返回值为java浮点数组
arlift<-function(rule) {
	J("net.c2001.dm.associationRule.verification.lift.Lift")$getLift(rule);
}

#计算并查看提升度
arviewlift<-function(rule) {
	arview2(rule, J("net.c2001.dm.associationRule.verification.lift.Lift")$getLift(rule), "提升度");
}

#启动字段名和基础知识库编辑器
areditrules<-function() {
	J("net.c2001.dm.associationRule.verification.novelty.NoveltyInfoEditor")$showNoveltyInfoEditor();
}

init();