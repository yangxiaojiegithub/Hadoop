package com.stanlong.adapter;

/**
 * 适配器模式   --类适配器模式
 * @author 矢量
 *
 * 2019年12月1日
 */
public class Adapter01 {
	public static void main(String[] args) {
		/*Phone phone = new Phone();
		phone.charging(new VoltageAdapter());*/
	}
}

/*// 输出220V电压, 被适配的类
class Voltage220V{
	public int output220V(){
		int src = 220;
		System.out.println("电压 = " + src + "伏");
		return src;
	}
}

// 适配接口
interface IVoltage5V{
	public int output5V();
}

// 适配器类
class VoltageAdapter extends Voltage220V implements IVoltage5V{

	@Override
	public int output5V() {
		// 获取过程 220V 电压
		int srcV = output220V();
		int dstV = srcV / 44; // 转成5V
		return dstV;
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
}*/