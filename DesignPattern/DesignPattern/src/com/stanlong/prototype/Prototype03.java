package com.stanlong.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 深拷贝
 * @author 矢量
 *
 * 2019年11月30日
 */
public class Prototype03 implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)  throws Exception {
		DeepPrototype p = new DeepPrototype();
		p.name = "宋江";
		p.deepCloneableTarget = new DeepCloneableTarget("大牛", "大牛的类");
		
		// 方式一完成深拷贝
		/*DeepPrototype p2= (DeepPrototype)p.clone();
		System.out.println("p.name: " + p.name + ", p.deepCloneableTarget.hashCode: " + p.deepCloneableTarget.hashCode());
		System.out.println("p2.name: " + p2.name + ", p2.deepCloneableTarget.hashCode: " + p2.deepCloneableTarget.hashCode());*/
		/*p.name: 宋江, p.deepCloneableTarget.hashCode: 705927765
		p2.name: 宋江, p2.deepCloneableTarget.hashCode: 366712642*/
		
		// 方式二完成深拷贝
		DeepPrototype p2= (DeepPrototype)p.deepClone();
		System.out.println("p.name: " + p.name + ", p.deepCloneableTarget.hashCode: " + p.deepCloneableTarget.hashCode());
		System.out.println("p2.name: " + p2.name + ", p2.deepCloneableTarget.hashCode: " + p2.deepCloneableTarget.hashCode());
		/*p.name: 宋江, p.deepCloneableTarget.hashCode: 1118140819
		p2.name: 宋江, p2.deepCloneableTarget.hashCode: 1956725890*/
	}
	
}


class DeepPrototype implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public DeepCloneableTarget deepCloneableTarget; // 引用类型
	public DeepPrototype() {
		super();
	}
	
	// 深拷贝 --方式1，使用clone方法
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object deep = null;
		// 完成对基本数据类型的克隆
		deep = super.clone();
		//对引用类型的属性进行单独处理
		DeepPrototype deepPrototype = (DeepPrototype)deep;
		deepPrototype.deepCloneableTarget = (DeepCloneableTarget)deepCloneableTarget.clone();
		
		return deepPrototype;
	}
	
	// 深拷贝 --方式2，使用序列化方法实现
	public Object deepClone(){
		// 创建流对象
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			// 序列化
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);  // 当前这个对象以对象流的方式输出
			
			// 反序列化
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			DeepPrototype copyObject = (DeepPrototype)ois.readObject();
			return copyObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			// 关闭流
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
}

class DeepCloneableTarget implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cloneName;
	
	private String cloneClass;

	public DeepCloneableTarget(String cloneName, String cloneClass) {
		this.cloneName = cloneName;
		this.cloneClass = cloneClass;
	}
	
	// 因为 DeepCloneableTarget 的属性都是String， 因此我们使用默认的clone完成即可
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}