import java.util.Random;

/**
 * 面试题：这是一个链表题，链表不光有next域还有一个指向任意链上任意一个结点的随机域random,
 * 注意random可以指向null，请完成对一个链表的复制。
 * 
 * 解答： 如果没有随机域random，这个题目就很简单，扫描一遍构建新结点即可，现在的难处在于复制 链表中random
 * 指向的结点可能还没创建，所以需要先创建所有结点并复制数据域，这一步只需 遍历一遍即可,然后再处理链接域
 * 方法1：	1、创建新结点newNode并复制数据域
 * 			2、保存oldList的链接状态
 * 			3、将oldList的oldNode结点next域指向对应的newNode
 * 			4、newNode.random = old.random.next
 * 			5、利用2保存的状态恢复oldList的next域
 * 方法1中第二2步需要一个O(n)的辅助空间。
 * 
 * 方法2：	1、在oldNode[i]与oldNode[i+1]插入newNode[i]，最后一个结点直接放在末尾
 * 			2、newNode.random = newNode.random.next建立复制链表的随机域
 * 			3、恢复两个链表的next域
 * 
 * @author jiqunpeng email jiqunpeng@gmail.com
 * 
 *  
 */
public class RandomLink {
	public RandomLink next = null;
	public RandomLink random = null;
	public long id = Long.MIN_VALUE;
	private static Random rand = new Random(47);

	/**
	 * 构造一个长度为n的链表
	 * 
	 * @param n
	 * @return
	 */
	public static RandomLink generator(int n) {
		RandomLink[] list = new RandomLink[n];
		for (int i = 0; i < n; i++) {
			list[i] = new RandomLink();
			list[i].id = i;
		}
		for (int i = 0; i < n; i++) {
			if (i == n - 1)// 最后一个结点next域为null
				list[i].next = null;
			else
				list[i].next = list[i + 1];
			int r = rand.nextInt(n + 1);
			if (r == n)
				list[i].random = null;
			else
				list[i].random = list[r];
		}
		return list[0];// 返回头结点
	}
	/**
	 * 按方法2复制链表
	 * @param oldList
	 * @return
	 */
	public static RandomLink copy(RandomLink oldList) {
		// 将新的结点插入到两个结点之间
		RandomLink oldNode = oldList;
		while (oldNode.next != null) {
			RandomLink newNode = new RandomLink();
			newNode.id = oldNode.id;
			newNode.next = oldNode.next;
			oldNode.next = newNode;
			oldNode = newNode.next;
		}
		// 最后一个新结点插入到末尾即可
		RandomLink lastNode = new RandomLink();
		lastNode.id = oldNode.id;
		lastNode.next = null;
		oldNode.next = lastNode;
		// 构造新链表的随机链域
		oldNode = oldList;
		RandomLink copyNode = oldList.next;
		RandomLink newNode;
		while (oldNode != null) {
			newNode = oldNode.next;
			// 复制新结点的随机链域
			if (oldNode.random == null)
				newNode.random = null;
			else
				newNode.random = oldNode.random.next;
			oldNode = oldNode.next.next;
		}
		// 恢复结点的next域
		oldNode = oldList;
		newNode = oldNode.next;
		while (true) {
			oldNode.next = newNode.next;// 重新链接旧结点的next
			oldNode = oldNode.next;// 下一个旧结点
			if (oldNode == null)
				break;
			newNode.next = oldNode.next;// 重新链接新结点的next
			newNode = newNode.next;// 下一个新结点
		}
		return copyNode;
	}

	public String getString() {
		return super.toString().split("@")[1];
	}

	@Override
	public String toString() {
		String nextId;
		if (next == null)
			nextId = "null";
		else
			nextId = Long.valueOf(next.id).toString() + "@" + next.getString();//
		String rId;
		if (random == null)
			rId = "null";
		else
			rId = Long.valueOf(random.id).toString() + "@" + random.getString();//
		return super.toString() + "\t" + id + "\t" + nextId + "\t" + rId;
	}

	public static void printList(RandomLink list) {
		RandomLink node = list;
		while (node != null) {
			System.out.println(node);
			node = node.next;
		}
	}

	public static void main(String[] args) {
		RandomLink list = RandomLink.generator(5);
		System.out.println("原始链表:");
		RandomLink.printList(list);
		RandomLink copyList = RandomLink.copy(list);
		System.out.println("复制后的原始链表:");
		RandomLink.printList(list);
		System.out.println("复制后的链表:");
		RandomLink.printList(copyList);
	}
}
