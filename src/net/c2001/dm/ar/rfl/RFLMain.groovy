package net.c2001.dm.ar.rfl

import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.garmf.Rule;


/**
 * Measures of itemsets and association rules.
 * @author Lin Dong
 *
 */
class Measures {
	/**
	 * Show all measures of itemset available.
	 * @return all measures of itemset available.
	 */
	def listItemsetMeasures(){
		def list = [
			"support",
			"item count",
			"all-confidence",
			"any-confidence",
			"information gain",
			"expectation",
			"standard deviation",
			"variance",
			"expectation independence"
		]
		return list
	}

	/**
	 * Show all measures of association rule available.
	 * @return all measures of association rule available.
	 */
	def listRuleMeasures(){
		def list = [
			"support",
			"confidence",
			"covariance",
			"correlation coefficient",
			"lift",
			"conviction",
			"interestingness",
			"interestingness",
			"IS",
			"gain",
			"validity",
			"improvement",
			"commonness",
			"contrast influence",
			"trust",
			"consult",
			"consult",
			"interest",
			"upgrade rate",
			"influence ratio",
		]
		return list
	}

	/**
	 * Short and full names of measures. 
	 */
	def nameMap =[
		//itemsets'
		"S":"support",
		"n":"item count",
		"All":"all-confidence",
		"Any":"any-confidence",
		"I":"information gain",
		"E":"expectation",
		"SD":"standard deviation",
		"D":"variance",
		"EI":"expectation independence",
		//rules'
		"RS":"support",
		"C":"confidence",
		"corr":"covariance",
		"r":"correlation coefficient",
		"L":"lift",
		"Conv":"conviction",
		"I1":"interestingness",
		"I2":"interestingness",
		"IS":"IS",
		"G":"gain",
		"V":"validity",
		"Imp":"improvement",
		"Com":"commonness",
		"CI":"contrast influence",
		"T":"trust",
		"C1":"consult",
		"C2":"consult",
		"I3":"interest",
		"Up":"upgrade rate",
		"IR":"influence ratio",
	]

	def private f = [:]

	/**
	 * Set the frequent itemsets to use.
	 * @param fsets frequent itemsets. 
	 */
	def void setFSets(fsets){
		f = [:]

		fsets.each {
			ItemSet iset = it
			f.putAt(iset,iset.getSupport())
		}

	}

	/**
	 * Get the total number of items in itemset.
	 * @param iset itemset.
	 * @return item count.
	 */
	def int n(ItemSet iset){
		return iset.getItemCount()
	}

	/**
	 * Get the support of itemset. To get the details please refer to
	 * (Agrawal et al. 1993).
	 * @param iset itemset.
	 * @return support.
	 */
	def float S(ItemSet iset){
		return f[iset]
	}

	/**
	 * Get the all-confidence of itemset. To get the details please refer to
	 * (Omiecinski 2003).
	 * @param iset itemset.
	 * @return all-confidence of itemset.
	 */
	def float All(ItemSet iset){
		def sup = S(iset)
		//It should be no less than sup
		def max = sup
		iset.items.each {
			def temp = new ItemSet()
			temp.addItem(it)
			if(f[temp]>max)
				max = f[temp]
			//println max
		}
		return sup/max
	}


	/**
	 * Get the any-confidence of itemset. To get the details please refer to
	 * (Omiecinski 2003).
	 * @param iset itemset.
	 * @return any-confidence of itemset.
	 */
	def float Any(ItemSet iset){
		def sup = f[iset]
		//It should be no less than sup
		def min = sup
		iset.getLargeSubsets().each {
			if(f[it]<min)
				min = f[it]
		}
		return sup/min
	}

	/**
	 * Get the information gain of given itemset. To get the details please refer to
	 * (Hao et al. 2005).
	 * @param iset itemset.
	 * @param weights weight of all items (global). If it is null all items will
	 * use 1.0 as weight. 
	 * @return information gain of itemset.
	 */
	def float I(ItemSet iset, float[] weights = null){
		def total = 0
		iset.getItems().each {
			def temp = new ItemSet()
			def weight = 1
			if(weights != null)
				weight = weights[it-1]
			temp.addItem(it)
			total += -Math.log10(f[temp])/Math.log10(2)*weight
		}
		return total
	}

	/**
	 * Get the expectation of given itemset.
	 * @param iset itemset.	
	 * @return expectation of itemset.
	 */
	def float E(ItemSet iset){
		return f[iset]
	}

	/**
	 * Get the variance of given itemset.
	 * @param iset itemset.
	 * @return variance of itemset.
	 */
	def float D(ItemSet iset){
		def s = S(iset)
		return s*(1-s)
	}

	/**
	 * Get the standard deviation of given itemset.
	 * @param iset itemset.
	 * @return standard deviation of itemset.
	 */
	def float SD(ItemSet iset){
		return Math.sqrt(D(iset))
	}

	/**
	 * Get the expectation of given itemset suppose all itemsets are independent.
	 * @param iset itemset.
	 * @return expectation of itemset under independent assumption.
	 */
	def float EI(ItemSet iset){
		def e = 1.0f
		iset.getItems().each {
			def temp = new ItemSet()
			temp.addItem(it)
			e *= f[temp]
		}
		return e
	}

	/**
	 * Get the support of rule. To get the details please refer to
	 * (Agrawal & Srikant 1994).
	 * @param r rule.
	 * @return rule support.
	 */
	def float RS(Rule r){
		def temp = new ItemSet()
		temp.addItems(r.src)
		temp.addItems(r.dst)
		return f[temp]
	}

	/**
	 * Get the confidence of rule. To get the details please refer to
	 * (Agrawal & Srikant 1994).
	 * @param r rule.
	 * @return rule confidence.
	 */
	def float C(Rule r){
		return RS(r)/f[r.src]
	}

	/**
	 * Get the covariance of rule. To get the details please refer to
	 * (Chen et al. 1996).
	 * @param r rule.
	 * @return rule covariance.
	 */
	def float corr(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return ab-a*b
	}
	
	/**
	 * Get the correlation coefficient of rule. 
	 * @param r rule.
	 * @return rule correlation coefficient.
	 */
	def float r(Rule r){
		def a = f[r.src]
		def b = f[r.dst]
		return corr(r)/Math.sqrt(a*(1-a)*b*(1-b))
	}

	/**
	 * Get the lift of rule. To get the details please refer to
	 * (Brin et al. 1997).
	 * @param r rule.
	 * @return rule lift.
	 */
	def float L(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return ab/a/b

	}

	/**
	 * Get the conviction of rule. To get the details please refer to
	 * (Brin et al. 1997).
	 * @param r rule.
	 * @return rule conviction.
	 */
	def float Conv(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return a*(1-b)/(a-ab)

	}

	/**
	 * Get the interestingness of rule. To get the details please refer to
	 * (Chen et al. 1996).
	 * @param r rule.
	 * @return rule interestingness.
	 */
	def float I1(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return ab/a - b
	}

	/**
	 * Get the interestingness of rule. To get the details please refer to
	 * (Zhou et al. 2000).
	 * @param r rule.
	 * @return rule interestingness.
	 */
	def float I2(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return (ab/a - b)/Math.max(ab/a, b)
	}

	/**
	 * Get IS of rule. To get the details please refer to
	 * (Tan & Kumar 2000).
	 * @param r rule.
	 * @return rule IS.
	 */
	def float IS(Rule r){
		return Math.sqrt(L(r)*RS(r))
	}

	/**
	 * Get gain of rule. To get the details please refer to
	 * (Brin & Rastogi 2003).
	 * @param r rule.
	 * @param minconf minimum confidence threshold.
	 * @return gain of rule.
	 */
	def float G(Rule r, float minconf){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return ab - minconf*a
	}

	/**
	 * Get validity of rule. To get the details please refer to
	 * (Luo & Wu 2002).
	 * @param r rule.
	 * @return validity of rule.
	 */
	def float V(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def b = f[r.dst]
		return 2*ab - b
	}

	/**
	 * Get the improvement of rule. To get the details please refer to
	 * (Bayardo & Agrawal 1999).
	 * @param r rule.
	 * @return improvement of rule.
	 */
	def float Imp(Rule r){
		def c = C(r)
		def r2 = new Rule()
		r2.dst = r.dst
		def max = 0
		def cls = {
			def ItemSet src = it
			r2.src = src
			def C2 = C(r2)
			if(C2>max)
				max = C2
			if(src.getItemCount() ==1)
				return;
			else for(ItemSet is:src.getLargeSubsets()){
					call(is)
				}
		}
		if(r.src.getItemCount() > 1){
			r.src.getLargeSubsets().each(cls)
		}
		return c - max
	}

	/**
	 * Get commonness of rule. To get the details please refer to
	 * (Klemettinen & Mannila 1994).
	 * @param r rule.
	 * @return commonness of rule.
	 */
	def float Com(Rule r){
		return RS(r)*C(r)
	}

	/**
	 * Get contrast influence of rule. To get the details please refer to
	 * (Zheng et al. 2009).
	 * @param r rule.
	 * @return contrast influence of rule.
	 */
	def float CI(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		def corr = corr(r)
		def corrsab = b - ab - (1-a)*b
		def corrasb = a - ab -a*(1-b)
		if(corr <1)
			return -1 + corr/corrsab
		else
			return 1 - corrasb/corr
	}

	/**
	 * Get trust of rule. To get the details please refer to
	 * (Liu et al. 2003).
	 * @param r rule.
	 * @return trust of rule.
	 */
	def float T(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		return ab/(a+b-ab)
	}

	/**
	 * Get consult of rule. To get the details please refer to
	 * (Liu & Song 2011).
	 * @param r rule.
	 * @return consult of rule.
	 */
	def float C2(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		def corr = L(r)
		def corr1 = (b-ab)/(1-a)/b
		return (corr-corr1)*b
	}


	/**
	 * Get consult of rule. To get the details please refer to
	 * (Lin et al. 2005).
	 * @param r rule.
	 * @return consult of rule.
	 */
	def float C1(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		def c = C(r)
		def d = (b-ab)/(1-a)
		return (c - d)/Math.max(c, d)
	}

	/**
	 * Get interest of rule. To get the details please refer to
	 * (Wang et al. 2008).
	 * @param r rule.
	 * @param confmin minimum confidence threshold.
	 * @return consult of rule.
	 */
	def float I3(Rule r,float confmin){
		return RS(r)-confmin
	}

	/**
	 * Get upgrade rate of rule. To get the details please refer to
	 * (Ma et al. 2007).
	 * @param r rule.
	 * @return upgrade rate of rule.
	 */
	def float Up(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		def c = C(r)
		def d = (b-ab)/(1-a)
		return c/d
	}

	/**
	 * Get influence ratio of rule. To get the details please refer to
	 * (Ma et al. 2000).
	 * @param r rule.
	 * @return influence ratio of rule.
	 */
	def float IR(Rule r){
		def u = r.src.clone()
		u.addItems(r.dst)
		def ab = f[u]
		def a = f[r.src]
		def b = f[r.dst]
		def c = C(r)
		def d = (b-ab)/(1-a)
		return (c-d)/(c+d)
	}

}


/**
 * Test the methods in this class using {@code assert}.
 */
def void simpleTest(){
	def ItemSet f = null
	def fsets = []
	//{1}, 0.5
	f = new ItemSet()
	f.addItem((short)1)
	f.setSupport(0.5)
	fsets<<f
	//{2}, 0.7
	f = new ItemSet()
	f.addItem((short)2)
	f.setSupport(0.7)
	fsets<<f
	//{3}, 0.6
	f = new ItemSet()
	f.addItem((short)3)
	f.setSupport(0.6)
	fsets<<f
	//{1,2}, 0.5
	f = new ItemSet()
	f.addItem((short)1)
	f.addItem((short)2)
	f.setSupport(0.5)
	fsets<<f
	//{1,3}, 0.4
	f = new ItemSet()
	f.addItem((short)1)
	f.addItem((short)3)
	f.setSupport(0.4)
	fsets<<f
	//{2,3}, 0.4
	f = new ItemSet()
	f.addItem((short)2)
	f.addItem((short)3)
	f.setSupport(0.3)
	fsets<<f
	//{1,2,3}, 0.3
	f = new ItemSet()
	f.addItem((short)1)
	f.addItem((short)2)
	f.addItem((short)3)
	f.setSupport(0.3)
	fsets<<f

	Measures m = new Measures();
	m.setFSets(fsets);
	//Itemset {1,2,3}
	def  i = new ItemSet()
	i.addItem((short)1)
	i.addItem((short)2)
	i.addItem((short)3)
	//Rule {1,2}==>{3}
	def  src = new ItemSet()
	src.addItem((short)1)
	src.addItem((short)2)
	def  dst = new ItemSet()
	dst.addItem((short)3)
	def r = new Rule()
	r.src = src
	r.dst = dst

	/*
	 All defs:
	 //{1}, 0.5
	 //{2}, 0.7
	 //{3}, 0.6
	 //{1,2}, 0.5
	 //{1,3}, 0.4
	 //{2,3}, 0.3
	 //{1,2,3}, 0.3
	 //Itemset {1,2,3}
	 //Rule {1,2}==>{3}	 
	 */	
	//Note float comparison is not easy.
	println "Evaluation measures for itemsets:"
	m.listItemsetMeasures().each { println it }
	println "Evaluation measures for rules:"
	m.listRuleMeasures().each { println it }
	println "No output below this line means all okay."
	//itemset measures
	assert(m.S(i)==0.3f)
	assert(m.n(i)==3)
	assert(m.All(i)==(float)(0.3f/0.7f))
	assert(m.Any(i)==(float)(0.3f/0.3f))
	assert(Math.abs(Math.pow(2, -m.I(i)) - 0.5*0.6*0.7)<0.001f)
	assert(m.E(i)==0.3f)
	assert(m.D(i)==(float)(0.3f*0.7f))
	assert(((float)(m.SD(i)*m.SD(i)))==m.D(i))
	assert(m.EI(i)==((float)(0.5f*0.7f*0.6f)))
	//rule measures
	assert(m.RS(r)==0.3f)
	assert(m.C(r)==((float)0.3f/0.5f))
	assert(m.corr(r)==((float)0.3f -0.5f*0.6f))
	assert(m.r(r)==((float)(m.corr(r)/Math.sqrt(0.5f*0.5f*0.6f*0.4f))))
	assert(m.L(r)==((float)0.3f/0.5f/0.6f))
	assert(m.Conv(r)==((float)0.5f*(1-0.6f)/(0.5f-0.3f)))
	assert(m.I1(r)==((float)0.3f/0.5f-0.6f))
	assert(m.I2(r)==((float)0f))
	assert(m.IS(r)==((float)Math.sqrt(0.3f/0.6f*0.3f/0.5f)))
	assert(m.G(r,0.6f)==((float)0.3f-0.5f*0.6f))
	assert(m.V(r)==((float)2f*0.3f-0.6f))
	assert((m.Imp(r) - (-0.2f))<0.001f)	
	assert(m.Com(r)==((float)0.6f*0.3f))
	//Note, NaN is possible
	assert(m.CI(r)==(Float.NaN))
	assert(m.T(r)==((float)0.3f/(0.6f+0.5f-0.3f)))
	assert(m.C1(r)==((float)0.3f/0.5f-0.3f/0.5f)/0.5f)
	assert(m.C2(r)==((float)0.6f*(m.L(r)-(0.3f/0.5f/0.6f))))
	assert(m.I3(r,0.6f)==((float)0.5f*0.3f/0.5f-0.6f))
	assert(m.Up(r)==((float)0.3f/0.5f/0.3f*0.5f))
	assert(m.IR(r)==((float)(0.3f/0.5f-0.3f/0.5f))/(0.3f/0.5f+0.3f/0.5f))
}
