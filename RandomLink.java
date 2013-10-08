import java.util.Random;

/**
 * �����⣺����һ�������⣬��������next����һ��ָ��������������һ�����������random,
 * ע��random����ָ��null������ɶ�һ������ĸ��ơ�
 * 
 * ��� ���û�������random�������Ŀ�ͺܼ򵥣�ɨ��һ�鹹���½�㼴�ɣ����ڵ��Ѵ����ڸ��� ������random
 * ָ��Ľ����ܻ�û������������Ҫ�ȴ������н�㲢������������һ��ֻ�� ����һ�鼴��,Ȼ���ٴ���������
 * ����1��	1�������½��newNode������������
 * 			2������oldList������״̬
 * 			3����oldList��oldNode���next��ָ���Ӧ��newNode
 * 			4��newNode.random = old.random.next
 * 			5������2�����״̬�ָ�oldList��next��
 * ����1�еڶ�2����Ҫһ��O(n)�ĸ����ռ䡣
 * 
 * ����2��	1����oldNode[i]��oldNode[i+1]����newNode[i]�����һ�����ֱ�ӷ���ĩβ
 * 			2��newNode.random = newNode.random.next������������������
 * 			3���ָ����������next��
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
	 * ����һ������Ϊn������
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
			if (i == n - 1)// ���һ�����next��Ϊnull
				list[i].next = null;
			else
				list[i].next = list[i + 1];
			int r = rand.nextInt(n + 1);
			if (r == n)
				list[i].random = null;
			else
				list[i].random = list[r];
		}
		return list[0];// ����ͷ���
	}
	/**
	 * ������2��������
	 * @param oldList
	 * @return
	 */
	public static RandomLink copy(RandomLink oldList) {
		// ���µĽ����뵽�������֮��
		RandomLink oldNode = oldList;
		while (oldNode.next != null) {
			RandomLink newNode = new RandomLink();
			newNode.id = oldNode.id;
			newNode.next = oldNode.next;
			oldNode.next = newNode;
			oldNode = newNode.next;
		}
		// ���һ���½����뵽ĩβ����
		RandomLink lastNode = new RandomLink();
		lastNode.id = oldNode.id;
		lastNode.next = null;
		oldNode.next = lastNode;
		// ������������������
		oldNode = oldList;
		RandomLink copyNode = oldList.next;
		RandomLink newNode;
		while (oldNode != null) {
			newNode = oldNode.next;
			// �����½����������
			if (oldNode.random == null)
				newNode.random = null;
			else
				newNode.random = oldNode.random.next;
			oldNode = oldNode.next.next;
		}
		// �ָ�����next��
		oldNode = oldList;
		newNode = oldNode.next;
		while (true) {
			oldNode.next = newNode.next;// �������Ӿɽ���next
			oldNode = oldNode.next;// ��һ���ɽ��
			if (oldNode == null)
				break;
			newNode.next = oldNode.next;// ���������½���next
			newNode = newNode.next;// ��һ���½��
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
		System.out.println("ԭʼ����:");
		RandomLink.printList(list);
		RandomLink copyList = RandomLink.copy(list);
		System.out.println("���ƺ��ԭʼ����:");
		RandomLink.printList(list);
		System.out.println("���ƺ������:");
		RandomLink.printList(copyList);
	}
}
