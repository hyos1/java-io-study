package review.reflection;

import review.reflection.data.Team;
import review.reflection.data.User;

public class FieldV4 {

    public static void main(String[] args) throws IllegalAccessException {
        User user = new User("id1", null, null);
        Team team = new Team("team1", null);
        System.out.println("===== before =====");
        System.out.println("user = " + user);
        System.out.println("team = " + team);
        System.out.println("===== after =====");
        FieldUtil.nullFieldToDefault(user);
        FieldUtil.nullFieldToDefault(team);
        System.out.println("user = " + user);
        System.out.println("team = " + team);
    }
}
