package com.autobots.automanager.services;

import java.util.List;


public interface LinkAdder<T> {
	public void addLink(List<T> lista);
	public void addLink(T objeto);
}