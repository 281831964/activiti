import com.cms.test.springboot.controller.UserController
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration

import org.springframework.mock.web.MockServletContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@RunWith(SpringJUnit4ClassRunner::class)
//@SpringApplicationConfiguration(classes = arrayOf(MockServletContext::class))
//@WebAppConfiguration
class ApplicationTests {

    private var mvc: MockMvc? = null

//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(UserController()).build()
//    }

//    @Test
//    @Throws(Exception::class)
    fun testUserController() {
        // 测试UserController
        var request: RequestBuilder? = null

        // 1、get查一下user列表，应该为空
        request = get("/users/")
        mvc!!.perform(request!!)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 2、post提交一个user
        request = post("/users/")
                .param("id", "1")
                .param("name", "测试大师")
                .param("age", "20")
        mvc!!.perform(request!!)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 3、get获取user列表，应该有刚才插入的数据
        request = get("/users/")
        mvc!!.perform(request!!)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 4、put修改id为1的user
        request = put("/users/1")
                .param("name", "测试终极大师")
                .param("age", "30")
        mvc!!.perform(request!!)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 5、get一个id为1的user
        request = get("/users/1")
        mvc!!.perform(request!!)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 6、del删除id为1的user
        request = delete("/users/1")
        mvc!!.perform(request!!)
                .andDo(MockMvcResultHandlers.print()).andReturn()

        // 7、get查一下user列表，应该为空
        request = get("/users/")
        mvc!!.perform(request!!)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print()).andReturn()
    }

}