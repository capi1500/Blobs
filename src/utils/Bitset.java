package utils;

import simulation.interfaces.Copyable;

import java.util.Arrays;

public class Bitset implements Copyable{
	private final boolean[] bits;
	
	// methods
	
	public static Bitset fromInt(int n){
		if(n == 0)
			return new Bitset(new boolean[]{false});
		
		int size = 0, tmp = n;
		while(tmp > 0){
			tmp /= 2;
			size++;
		}
		Bitset bitset = new Bitset(size);
		size--;
		while(n > 0){
			bitset.set(size--, n % 2 == 1);
			n /= 2;
		}
		
		return bitset;
	}
	
	public int getLength(){
		return bits.length;
	}
	
	public int value(){
		int value = 0;
		for(boolean bit : bits){
			value *= 2;
			value += (bit ? 1 : 0);
		}
		return value;
	}
	
	public Bitset or(Bitset bitset){
		if(bitset.getLength() > getLength())
			return bitset.or(this);
		Bitset out = new Bitset(this);
		for(int i = 1; i <= bitset.getLength(); i++)
			out.bits[out.bits.length - i] |= bitset.bits[bitset.bits.length - i];
		return out;
	}
	
	public Bitset xor(Bitset bitset){
		if(bitset.getLength() > getLength())
			return bitset.xor(this);
		Bitset out = new Bitset(this);
		for(int i = 1; i <= bitset.getLength(); i++)
			out.bits[out.bits.length - i] ^= bitset.bits[bitset.bits.length - i];
		return out;
	}
	
	public Bitset and(Bitset bitset){
		if(bitset.getLength() > getLength())
			return bitset.and(this);
		Bitset out = new Bitset(this);
		for(int i = 1; i <= bitset.getLength(); i++)
			out.bits[out.bits.length - i] &= bitset.bits[bitset.bits.length - i];
		return out;
	}
	
	public boolean at(int i){
		assert 0 <= i && i <= getLength();
		return bits[i];
	}
	
	public void set(int i, boolean v){
		assert 0 <= i && i <= getLength();
		bits[i] = v;
	}
	
	// constructors
	
	public Bitset(boolean[] bits){
		this.bits = bits;
	}
	
	public Bitset(int size){
		bits = new boolean[size];
		for(boolean bit : bits)
			bit = false;
	}
	
	private Bitset(Bitset copy){
		bits = new boolean[copy.bits.length];
		for(int i = 0; i < bits.length; i++)
			bits[i] = copy.bits[i];
	}
	
	// overrides
	
	@Override
	public Bitset copy(){
		return new Bitset(this);
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		
		Bitset bitset = (Bitset)o;
		
		return Arrays.equals(bits, bitset.bits);
	}
	
	@Override
	public String toString(){
		String out = "";
		for(int i = 0; i < bits.length; i++)
			out += bits[i] ? "1" : "0";
		return out;
	}
}
