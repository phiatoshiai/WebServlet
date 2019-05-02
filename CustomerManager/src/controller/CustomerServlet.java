package controller;

import model.Customer;
import service.CustomerService;
import service.CustomerServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customers-manager")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerServiceImpl ();

    protected void doPost ( HttpServletRequest req , HttpServletResponse resp ) throws ServletException, IOException {
        String action = req.getParameter ( "action" );
        if ( action == null ) {
            action = "";
        }
        switch (action) {
            case "edit":
                updateCustomer ( req , resp );
                break;
            case "create":
                createCustomer ( req , resp );
            default:
                break;
        }
    }


    protected void doGet ( HttpServletRequest req , HttpServletResponse resp ) throws ServletException, IOException {
        String action = req.getParameter ( "action" );
        if ( action == null ) {
            action = "";
        }
        switch (action) {
            case "edit":
                showEditForm ( req , resp );
                break;
            case "delete":
                showDeleteForm ( req , resp );
                break;
            case "search":
                searchCustomers ( req , resp );
                break;
            default:
                listCustomers ( req , resp );
        }
    }

    private void listCustomers ( HttpServletRequest request , HttpServletResponse response ) {
        List<Customer> customerList = customerService.findAll ();
        request.setAttribute ( "customerList" , customerList );
        RequestDispatcher dispatcher = request.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        try {
            dispatcher.forward ( request , response );
        } catch (ServletException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void createCustomer ( HttpServletRequest req , HttpServletResponse resp ) {
        String name = req.getParameter ( "name" );
        String email = req.getParameter ( "email" );
        String address = req.getParameter ( "address" );
        int id = (int) (Math.random () * 10000);
        Customer customer = new Customer ( id , name , email , address );
        customerService.save ( customer );
        List<Customer> customerList = customerService.findAll ();
        req.setAttribute ( "customerList" , customerList );
        RequestDispatcher dispatcher = req.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        req.setAttribute ( "messagecreate" , "Khách hàng mới được tạo" );
        try {
            dispatcher.forward ( req , resp );
        } catch (ServletException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void showEditForm ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
        int id = Integer.parseInt ( request.getParameter ( "id" ) );
        RequestDispatcher dispatcher;
        request.setAttribute ( "customerList" , customerService.findAll () );
        request.setAttribute ( "editId" , id );
        dispatcher = request.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        dispatcher.forward ( request , response );

    }

    private void updateCustomer ( HttpServletRequest request , HttpServletResponse response ) throws IOException {
        int id = Integer.parseInt ( request.getParameter ( "id" ) );
        String name = request.getParameter ( "name" );
        String email = request.getParameter ( "email" );
        String address = request.getParameter ( "address" );
        Customer customer = this.customerService.findById ( id );
        RequestDispatcher dispatcher;
        if ( customer == null ) {
            dispatcher = request.getRequestDispatcher ( "index.jsp" );
        } else {
            customer.setName ( name );
            customer.setEmail ( email );
            customer.setAddress ( address );
            this.customerService.update ( id , customer );
            request.setAttribute ( "customerList" , customerService.findAll () );
            int editId = -1;
            request.setAttribute ( "editId" , editId );
            dispatcher = request.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        }
        try {
            dispatcher.forward ( request , response );
        } catch (ServletException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }


    private void showDeleteForm ( HttpServletRequest request , HttpServletResponse response ) {
        int id = Integer.parseInt ( request.getParameter ( "id" ) );
        Customer customer = customerService.findById ( id );
        RequestDispatcher dispatcher;
        if ( customer == null ) {
            dispatcher = request.getRequestDispatcher ( "index.jsp" );
        } else {
            customerService.remove ( id );
            request.setAttribute ( "customerList" , customerService.findAll () );
            dispatcher = request.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        }
        try {
            dispatcher.forward ( request , response );
        } catch (ServletException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void searchCustomers ( HttpServletRequest request , HttpServletResponse response ) throws IOException, ServletException {

        String reqId = request.getParameter ( "id" );
        List<Customer> customerList = new ArrayList<> ();
        if ( reqId.equals ( "" ) ) {
            customerList = customerService.findAll ();
            request.setAttribute ( "reqId" , reqId );
        } else {
            int id = Integer.parseInt ( reqId );
            customerList.add ( customerService.findById ( id ) );

        }
        request.setAttribute ( "customerList" , customerList );
        RequestDispatcher requestDispatcher = request.getRequestDispatcher ( "/WEB-INF/list.jsp" );
        requestDispatcher.forward ( request , response );
    }
}


