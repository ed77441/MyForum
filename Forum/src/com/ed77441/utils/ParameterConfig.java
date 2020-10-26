package com.ed77441.utils;

import java.util.ArrayList;
import java.util.List;

public class ParameterConfig {
	List <ParameterConstraint> getConstraints = new ArrayList<>(); 
	List <ParameterConstraint> postConstraints = new ArrayList<>(); 
	List <ParameterConstraint> putConstraints = new ArrayList<>(); 
	List <ParameterConstraint> deleteConstraints = new ArrayList<>(); 

	public void setGetConstraints(ParameterConstraint...constraints) {
		for (ParameterConstraint constraint: constraints) {
			getConstraints.add(constraint);
		}
	}
	
	public void setPostConstraints(ParameterConstraint...constraints) {
		for (ParameterConstraint constraint: constraints) {
			postConstraints.add(constraint);
		}
	}
	
	public void setPutConstraints(ParameterConstraint...constraints) {
		for (ParameterConstraint constraint: constraints) {
			putConstraints.add(constraint);
		}
	}
	
	public void setDeleteConstraints(ParameterConstraint...constraints) {
		for (ParameterConstraint constraint: constraints) {
			deleteConstraints.add(constraint);
		}
	}
	
	public List<ParameterConstraint> getGetConstraints() {
		return getConstraints;
	}

	public List<ParameterConstraint> getPostConstraints() {
		return postConstraints;
	}

	public List<ParameterConstraint> getPutConstraints() {
		return putConstraints;
	}

	public List<ParameterConstraint> getDeleteConstraints() {
		return deleteConstraints;
	}
}
