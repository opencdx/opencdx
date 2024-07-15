/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.communications.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpenCDXHTMLProcessorImplTest {

    OpenCDXHTMLProcessorImpl openCDXHTMLProcessor = new OpenCDXHTMLProcessorImpl();

    @Test
    void processHTML() {
        String template = "<!DOCTYPE html>\n" + "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n"
                + "<head>\n"
                + "<meta charset=\"ISO-8859-1\">\n"
                + "<title>Employee</title>\n"
                + "<link rel=\"stylesheet\"\n"
                + "    href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\"\n"
                + "    integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\"\n"
                + "    crossorigin=\"anonymous\">\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class=\"container my-2\" align=\"center\">\n"
                + "<h3>Employee List</h3>\n"
                + "<a th:href=\"addnew\" class=\"btn btn-primary btn-sm mb-3\" >Add Employee</a>\n"
                + "    <table style=\"width:80%\" border=\"1\"\n"
                + "           class = \"table table-striped table-responsive-md\">\n"
                + "    <thead>\n"
                + "  <tr>\n"
                + "    <th>Name</th>\n"
                + "    <th>Email</th>\n"
                + "    <th>Action</th>\n"
                + "  </tr>\n"
                + "  </thead>\n"
                + "  <tbody>\n"
                + "  <tr th:each=\"employee:${allemplist}\">\n"
                + "        <td th:text=\"${employee.name}\"></td>\n"
                + "        <td th:text=\"${employee.email}\"></td>\n"
                + "  </tr>\n"
                + "  </tbody>\n"
                + "</table>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        Employee employee = new Employee();
        employee.setEmail("Email");
        employee.setName("Name");
        Employee employee1 = new Employee();
        employee1.setEmail("Email1");
        employee1.setName("Name1");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("allemplist", employeeList);
        String replacedTemplate = openCDXHTMLProcessor.processHTML(template, employeeMap);
        assertTrue(replacedTemplate.contains("Name1"));
        assertTrue(replacedTemplate.contains("Email1"));
    }

    class Employee {
        String name;
        String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
