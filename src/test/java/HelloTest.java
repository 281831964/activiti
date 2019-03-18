import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloTest.class)
public class HelloTest {


    private ProcessEngine processEngine;


    @Before
    public void getHello() throws Exception {
        ProcessEngineConfiguration engineConfiguration= ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //设置数据库连接属性
        engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti_cmdb?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("carigold");
        engineConfiguration.setJdbcMaxIdleConnections(100);
        engineConfiguration.setJdbcMaxActiveConnections(50);

        // 设置创建表的策略 （当没有表时，自动创建表）
        engineConfiguration.setDatabaseSchemaUpdate("true");
        //通过ProcessEngineConfiguration对象创建 ProcessEngine 对象
        processEngine = engineConfiguration.buildProcessEngine();
        System.out.println("流程引擎创建成功!");
    }


    @Test
    public void deploy() {

        //根据acitivit disiger 插件管理流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()//创建一个部署的构建器
                .addClasspathResource("disgrams/NewShopping.bpmn")//从类路径中添加资源,一次只能添加一个资源
                .name("采购流程")//设置部署的名称
                .category("采购")//设置部署的类别
                .deploy();

        System.out.println("部署的id"+deploy.getId());
        System.out.println("部署的名称"+deploy.getName());
    }

    @Test
    public void startProcess(){

        //指定执行我们刚才部署的工作流程
        String processDefiKey="TestActiviti";
        //取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //取得流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefiKey);//通过流程定义的key 来执行流程
        System.out.println("流程实例id:"+pi.getId());//流程实例id
        System.out.println("流程定义id:"+pi.getProcessDefinitionId());//输出流程定义的id



//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("employeeName", "Kermit");
//        variables.put("numberOfDays", new Integer(4));
//        variables.put("vacationMotivation", "I'm really tired!");
//
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ForkShopping", variables);
//
//        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());



    }


    //查询任务
    @Test
    public void queryTask(){
        //任务的办理人
        String assignee="kowi";
        //取得任务服务
        TaskService taskService = processEngine.getTaskService();
        //创建一个任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //办理人的任务列表
        List<Task> list = taskQuery.taskAssignee(assignee).list();//指定办理人

        //遍历任务列表
        if(list!=null&&list.size()>0){
            for(Task task:list){
                System.out.println("任务的办理人："+task.getAssignee());
                System.out.println("任务的id："+task.getId());
                System.out.println("任务的名称："+task.getName());
            }
        }
    }

    //完成任务
    @Test
    public void compileTask(){
        String taskId="47502";
        //taskId：任务id
//        Map<String,Object> params=new HashMap<>();
//        params.put("message",2);流程中有分支的控制参数设置
//        processEngine.getTaskService().complete(taskId,params);
        processEngine.getTaskService().complete(taskId);
        System.out.println("当前任务执行完毕");
    }


    @Test
    public void viewImage() throws Exception{
        String deploymentId="17501";
        String imageName=null;
        //取得某个部署的资源的名称  deploymentId
        List<String> resourceNames = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        // buybill.bpmn  buybill.png
        if(resourceNames!=null&&resourceNames.size()>0){
            for(String temp :resourceNames){
                if(temp.indexOf(".png")>0){
                    imageName=temp;
                }
            }
        }

        /**
         * 读取资源
         * deploymentId:部署的id
         * resourceName：资源的文件名
         */
        InputStream resourceAsStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);

        //把文件输入流写入到文件中
        File file=new File("d:/"+imageName);
        FileUtils.copyInputStreamToFile(resourceAsStream, file);
    }

    //查看流程定义
    @Test
    public void queryProcessDefination(){
        String processDefiKey="autoBpmn";//流程定义key
        //获取流程定义列表
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery()
                //查询 ，好比where
//        .processDefinitionId(proDefiId) //流程定义id
                // 流程定义id  ： buyBill:2:704   组成 ： proDefikey（流程定义key）+version(版本)+自动生成id
                .processDefinitionKey(processDefiKey)//流程定义key 由bpmn 的 process 的  id属性决定
//        .processDefinitionName(name)//流程定义名称  由bpmn 的 process 的  name属性决定
//        .processDefinitionVersion(version)//流程定义的版本
                .latestVersion()//最新版本
                //排序
                .orderByProcessDefinitionVersion().desc()//按版本的降序排序

                //结果
//        .count()//统计结果
//        .listPage(arg0, arg1)//分页查询
                .list();


        //遍历结果
        if(list!=null&&list.size()>0){
            for(ProcessDefinition temp:list){
                System.out.print("流程定义的id: "+temp.getId());
                System.out.print("流程定义的key: "+temp.getKey());
                System.out.print("流程定义的版本: "+temp.getVersion());
                System.out.print("流程定义部署的id: "+temp.getDeploymentId());
                System.out.println("流程定义的名称: "+temp.getName());
            }
        }
    }


}
