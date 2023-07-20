package hellospring.hello1;

import hellospring.hello1.repository.JdbcMemberRepository;
import hellospring.hello1.repository.MemberRepository;
import hellospring.hello1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return (MemberRepository) new JdbcMemberRepository(dataSource);
    }
}
