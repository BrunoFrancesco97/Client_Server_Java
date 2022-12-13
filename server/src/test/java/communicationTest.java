import org.example.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class communicationTest {
    private final int PORT = 9002;
    @Test
    public void testHandleName() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<>("test","NAME","test"));
            out.flush();
            out.reset();
            Message<String> result = (Message<String>) in.readObject();
            if(result.getEvent() != null && !result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("mode",result.getEvent().toLowerCase())
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleStartGame() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","START","practice"));
            out.flush();
            out.reset();
            Message result1 = (Message<String>) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result1.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("game",result1.getEvent().toLowerCase()),
                    ()->Assertions.assertTrue(result1.getMessage() instanceof Question)
            );
            out.writeObject(new Message<String>("test","START","friendlytournament"));
            out.flush();
            out.reset();
            Message result2 = (Message<String>) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result2.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("list",result2.getEvent().toLowerCase()),
                    ()->Assertions.assertTrue(result2.getMessage() instanceof ArrayList<?>)
            );
            out.writeObject(new Message<String>("test","START","test"));
            out.flush();
            out.reset();
            Message result3 = (Message<String>) in.readObject();
            System.out.println(result3);
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result3.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("error",result3.getEvent().toLowerCase()),
                    ()->Assertions.assertNull(result3.getMessage())
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleGaming() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<Question>("test","GAME",new Question("quest","answer")));
            out.flush();
            out.reset();
            Message result1 = (Message<String>) in.readObject();
            System.out.println(result1);
            if(!result1.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result1.getOwner().toLowerCase()),
                        ()->Assertions.assertTrue(result1.getEvent().toLowerCase().equals("game") || result1.getEvent().toLowerCase().equals("end")),
                        ()->Assertions.assertTrue(result1.getMessage() instanceof Question || result1.getMessage() instanceof Score)
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result1.getOwner().toLowerCase()),
                        ()->Assertions.assertTrue(result1.getEvent().toLowerCase().equals("error")),
                        ()->Assertions.assertTrue(result1.getMessage() == null)
                );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleEndTimer() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","END_TIMER","test"));
            out.flush();
            out.reset();
            Message<String> result = (Message<String>) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("end_timer",result.getEvent().toLowerCase()),
                    ()->Assertions.assertNull(result.getMessage())
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleContinueGame() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<>("test","RESUME","practice"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("game",result.getEvent().toLowerCase()),
                        ()->Assertions.assertTrue(result.getMessage() instanceof Question)
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())
                );
            }
            out.writeObject(new Message<>("test","RESUME","test"));
            out.flush();
            out.reset();
            Message result2 = (Message) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result2.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("error",result2.getEvent().toLowerCase()),
                    ()->Assertions.assertNull(result2.getMessage())
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testNameChecker() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","NAME_CHECKER","test"));
            out.flush();
            out.reset();
            Message<String> result = (Message<String>) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("name_checker",result.getEvent().toLowerCase())

            );
            if(result.getMessage() != null)
                Assertions.assertEquals("y",result.getMessage().toLowerCase());
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleCreate() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","CREATE","test:2:1:5:practice"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("create",result.getEvent().toLowerCase()),
                    ()->Assertions.assertTrue(result.getMessage() instanceof Match)

            );
            out.writeObject(new Message<String>("test","CREATE","test:2:1:5"));
            out.flush();
            out.reset();
            Message result2 = (Message) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result2.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("error",result2.getEvent().toLowerCase()),
                    ()->Assertions.assertNull(result2.getMessage())
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleMatchRemover() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","MATCH_REMOVER","test"));
            out.flush();
            out.reset();
            Message<String> result = (Message<String>) in.readObject();
            System.out.println(result);
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("match_remover",result.getEvent().toLowerCase()),
                        ()->Assertions.assertEquals("ok",result.getMessage().toLowerCase())

                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleGetIn() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","GET_IN","test"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("get_in",result.getEvent().toLowerCase())
                );
                if(result.getMessage() != null){
                    Assertions.assertTrue(result.getMessage() instanceof Match);
                }
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleUpdate() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","UPDATE_PLAYERS","test"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("update_players",result.getEvent().toLowerCase()),
                        ()->Assertions.assertTrue(result.getMessage() instanceof Match)
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testDropQuestion() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","DROP_QUESTION","test"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            System.out.println(result);
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("drop_question",result.getEvent().toLowerCase()),
                        ()->Assertions.assertTrue(result.getMessage() instanceof Question)
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleIsEnd() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","IS_END","test"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("is_end",result.getEvent().toLowerCase())
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testUpdateNext() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<String>("test","UPDATE_NEXT","test"));
            out.flush();
            out.reset();
            Message result = (Message) in.readObject();
            if(!result.getEvent().toLowerCase().equals("error")){
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("return_rank",result.getEvent().toLowerCase()),
                        ()->Assertions.assertTrue(result.getMessage() instanceof ArrayList<?>)
                );
            }else{
                Assertions.assertAll(
                        ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                        ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                        ()->Assertions.assertNull(result.getMessage())

                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }

    @Test
    public void testHandleOthers() throws IOException {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Message<>("test","test","test"));
            out.flush();
            out.reset();
            Message<String> result = (Message<String>) in.readObject();
            Assertions.assertAll(
                    ()->Assertions.assertEquals("test",result.getOwner().toLowerCase()),
                    ()->Assertions.assertEquals("error",result.getEvent().toLowerCase()),
                    ()->Assertions.assertNull(result.getMessage())

            );
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(socket != null && out != null && in != null){
                socket.close();
                in.close();
                out.close();
            }
        }
    }
}
