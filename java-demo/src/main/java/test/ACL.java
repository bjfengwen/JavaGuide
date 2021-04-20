package test;

public class ACL {

	private int id;// ACL的主键

	private String principalType; // 主体类型 角色

	private int principalSn; // 主体标识 角色id

	private int resourceSn; // 资源标识 模块id

	// 授权状态 用后四位bit 来表示 c创建r读取u修改d删除

	private int aclState;

	/**
	  * acl实例跟主体和资源关联
	  * 针对此实例进行授权：某种操作是否允许
	  * @param permission 只可以取值0,1,2,3
	  * @param yes true表示允许，false表示不允许
	  */
	 public void setPermission(int permission,boolean yes){
	  int tmp = 1;
	  //tmp的二进制形式向左移动permission个单位
	  //这样经过移动的结果会有四种情况: C:0001 R:0010 U:0100 D:1000
	  tmp = tmp << permission;
	  if(yes){
	   //如果是授权,则把原有的权限与当前的权限相加,二进制用"|"
	   aclState |= tmp;
	  }else{
	   //如果是减去授权,则当前传进来的权限取反,再与原有的权限"&"
	   aclState &= ~tmp;
	  }
	 }

	// 得到每一位的权限：如果那一位上允许我们返回一，否则返回0
	 /**
	  * 获得ACL授权(获得C/R/U/D的权限是否允许或是否确定)
	  * @param permission C/R/U/D权限
	  * @return 授权标识：允许/不允许/不确定
	  */
	public int getPermission(int permission) {

		int tmp = 1;

		tmp = tmp << permission;
		  //只要C/R/U/D中不全部为没有权限(0000),那么与刚刚传入的权限相"&"是不会出现等于0的.
		tmp &= aclState;

		if (tmp != 0) {

			return 1;

		} 
		return 0;

	}
	
	
	  

}