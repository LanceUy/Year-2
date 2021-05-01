package ControllerLayer;

public class LoginScreen {

    /**
     * Outputs requests for user to enter username and password. Starts hierarchy of program.
     *
//     * @param am Instance of UseCases.AttendeeManager with loaded information
//     * @param om Instance of UseCases.OrganizerManager with loaded information
//     * @param sm Instance of UseCases.SpeakerManager with loaded information
//     * @param es String of the username of current user logged in
//     * @param vm Instance of UseCases.VipManager with loaded information
     */
//    public void run(UseCases.AttendeeManager am, UseCases.OrganizerManager om, UseCases.SpeakerManager sm, UseCases.EventScheduler es, UseCases.VipManager vm){
//        System.out.println("Enter username");
//        ControllerLayer.LoginType login = new ControllerLayer.LoginType();
//        login.readUsername();
//        System.out.println("Enter password");
//        login.readPassword();
//        login.checkLogin(am, om, sm, es, vm);
//    }
    public void enterUsername(){
        System.out.println("Enter username");
    }

    public void enterPw(){
        System.out.println("Enter password");
    }
}
