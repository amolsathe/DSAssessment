package test;

import comp2402a2.BlockedList;

public class Test {

    public static void main(String[] args) {
    	BlockedList<Integer> blockedList= new BlockedList<Integer>(Integer.class, 53);
    	for (int i = 0; i < 10; i++) {
			blockedList.add(i+1);
		}
    	for (int i = 0; i < blockedList.size(); i++) {
			System.out.println("At i="+i+" x="+blockedList.get(i));
		}
    	//System.out.println(blockedList.size());
    	//System.out.println(blockedList.get(22));
    	//System.out.println("removed "+blockedList.remove(9));
    	blockedList.add(10, 111);
    	//System.out.println(blockedList.set(0, 123));
    	//System.out.println(blockedList.get(0));
    	System.out.println("size = "+blockedList.size());
    	for (int i = 0; i < blockedList.size(); i++) {
    		System.out.println("At i="+i+" x="+blockedList.get(i));
		}
    	
    }
}
