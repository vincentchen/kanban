package com.idealism.kanban.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OperationList {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List MergeOneDimensionalArrayList(List list1,List list2) {
		Set set = new HashSet();
		List newList = new ArrayList();
		Iterator it1 = list1.iterator();
		while (it1.hasNext()) {
			Object element = it1.next();
			if (set.add(element)){
				newList.add(element);
			}
		}
		Iterator it2 = list2.iterator();
		while (it2.hasNext()) {
			Object element = it2.next();
			if (set.add(element)){
				newList.add(element);
			}
		}
		return newList;
	}
}
