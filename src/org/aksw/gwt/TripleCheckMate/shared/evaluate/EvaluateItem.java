package org.aksw.gwt.TripleCheckMate.shared.evaluate;

import java.io.Serializable;

import org.aksw.gwt.TripleCheckMate.shared.sparql.ResultItem;

import com.google.gwt.view.client.ProvidesKey;

public class EvaluateItem implements Comparable<EvaluateItem> , Serializable{
	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 147695789864172749L;
	
	public long ID = 0;
	public ResultItem S = null;
	public ResultItem P = null;
	public ResultItem O = null;
	public long errorType = 0;
	public String errorTittle = "";
	public String comments = "";
	public boolean isWrong = false;
	
	
	/**
     * The key provider that provides the unique ID of an Evaluate Item.
     */
    public static final ProvidesKey<EvaluateItem> KEY_PROVIDER = new ProvidesKey<EvaluateItem>() {
      public Object getKey(EvaluateItem item) {
        return item == null ? null : item.ID;
      }
    };
	
    public EvaluateItem(){
		
	}
    
    public EvaluateItem(long id, ResultItem s, ResultItem p,ResultItem o){
		ID=id;
		S=s;
		P=p;
		O=o;
		isWrong = false;
	}
	
	@Override
	public int compareTo(EvaluateItem item) {
		if (item == null )
			return -1;
		int cmpS = item.S.value.compareTo(S.value);
		if (cmpS != 0) return cmpS;
		
		int cmpP = item.S.value.compareTo(P.value);
		if (cmpP != 0) return cmpP;
		
		int cmpO = item.S.value.compareTo(O.value);
		if (cmpO != 0) return cmpO;

		return 0;
	}
}
