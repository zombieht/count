package count;

public class Main {
	public static void main(String[] args) {

		Service t = new Service();
		try {
			if (args.length == 5) {
				t.findAll(args[0], args[1], args[2], args[3], args[4]);
			} else if (args.length == 4) {
				t.findAll(args[0], args[1], args[2], args[3], null);
			} else {
				System.out.println("参数格式:（driver url name password table）");
				// t.findAll("pgsql", "192.168.1.250:5432/cpm_db", "cpm", "cpm", null);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
