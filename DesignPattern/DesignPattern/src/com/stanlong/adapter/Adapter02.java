package com.stanlong.adapter;

/**
 * 适配器模式 --》对象适配器
 * @author 矢量
 *
 * 2019年12月1日
 */
public class Adapter02 {
	public static void main(String[] args) {
		Phone phone = new Phone();
		phone.charging(new VoltageAdapter(new Voltage220V()));
	}
}

//输出220V电压, 被适配的类
class Voltage220V{
	public int output220V(){
		int src = 220;
		System.out.println("电压 = " + src + "伏");
		return src;
	}
}

//适配接口
interface IVoltage5V{
	public int output5V();
}

class VoltageAdapter implements IVoltage5V{
	
	private Voltage220V voltage220V; // 关联关系中的聚合关系
	
	// 通过构造器传入一个 voltage220V 的实例
	public VoltageAdapter(Voltage220V voltage220V){
		this.voltage220V = voltage220V;
	}

	@Override
	public int output5V() {
		int dst = 0;
		if(null != voltage220V){
			int src = voltage220V.output220V();
			dst = src / 44;
			System.out.println("适配完成，输出的电压为=" + dst);
		}
		return dst;
	}
}

class Phone{
	public void charging(IVoltage5V iVoltage5V){
		if(iVoltage5V.output5V() == 5){
			System.out.println("电压5V，可以充电");
		}else{
			System.out.println("电压不匹配，无法充电");
		}
	}
}