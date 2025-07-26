package com.gaslab.config;

import com.gaslab.model.Situation;
import com.gaslab.model.Statement;
import com.gaslab.repository.SituationRepository;
import com.gaslab.repository.StatementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(SituationRepository situationRepo, StatementRepository statementRepo) {
        return args -> {
            if (situationRepo.count() > 0) {
                return;
            }
            Situation relationship = new Situation();
            relationship.setName("relationship");
            relationship = situationRepo.save(relationship);

            Situation workplace = new Situation();
            workplace.setName("workplace");
            workplace = situationRepo.save(workplace);

            Situation family = new Situation();
            family.setName("family");
            family = situationRepo.save(family);

            // 연인관계 가스라이팅
            String[] relationshipStatements = {
                "너 때문에 내 인생이 망가졌어. 너만 아니었으면 난 지금쯤 훨씬 더 잘 살고 있었을 거야.",
                "네가 너무 예민해서 그래. 다른 사람들은 다 괜찮다는데 왜 너만 그러니?",
                "내가 언제 그랬다고? 너 또 상상한 거 아니야? 맨날 없는 일을 지어내네.",
                "너랑 있으면 숨이 막혀. 왜 이렇게 집착하니? 정상적인 사람은 이렇게 안 해.",
                "내 친구들도 다 네가 이상하다고 해. 나만 참고 있는 거야.",
                "네가 날 이렇게 만든 거야. 원래 난 이런 사람이 아니었어.",
                "너 정신병원 가봐야 하는 거 아니야? 진짜 정상이 아닌 것 같아.",
                "내가 너한테 얼마나 잘해줬는데? 이렇게 고마움도 모르니?",
                "너 같은 사람이랑 사귀어주는 것만으로도 감사해야지. 누가 너랑 사귀겠어?",
                "네가 날 의심하니까 내가 그런 생각을 하게 되는 거야. 다 네 탓이야."
            };

            for (String content : relationshipStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(relationship);
                statementRepo.save(stmt);
            }

            // 직장 가스라이팅
            String[] workplaceStatements = {
                "이것도 못하면서 월급은 받아가니? 양심이 있어?",
                "다른 팀원들은 다 잘하는데 왜 너만 못하니? 능력이 부족한 거 아니야?",
                "내가 언제 그런 지시를 했어? 너 혼자 착각한 거지. 녹음이라도 있어?",
                "네가 있으면 팀 분위기가 다운돼. 다들 불편해하는 거 모르니?",
                "이 정도도 스트레스 받으면 어떻게 직장생활 해? 멘탈이 너무 약한 거 아니야?",
                "내가 너 때문에 위에서 얼마나 욕먹는지 알아? 너 하나 때문에 팀 전체가 피해봐.",
                "네가 무능해서 내가 이렇게 화내는 거야. 일을 잘했으면 내가 왜 화를 내겠어?",
                "다른 회사 가봐. 누가 너 같은 사람 써주겠어? 여기라도 다니는 게 다행인 줄 알아.",
                "내가 너를 가르치느라 내 시간을 얼마나 낭비하는지 알아? 진짜 답답해 죽겠어.",
                "팀워크를 망치는 건 바로 너야. 다들 너 때문에 힘들어해."
            };

            for (String content : workplaceStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(workplace);
                statementRepo.save(stmt);
            }

            // 가족 가스라이팅
            String[] familyStatements = {
                "너 때문에 우리 집안이 망신이야. 부모 얼굴에 먹칠하는 것도 정도가 있지.",
                "네 동생/형제는 잘하는데 왜 너만 이 모양이니? 같은 부모 밑에서 자랐는데.",
                "내가 널 어떻게 키웠는데 이렇게 은혜도 모르니? 불효자식이 따로 없네.",
                "네가 태어나지 않았으면 우리 가족이 더 행복했을 거야.",
                "너는 왜 맨날 문제만 일으키니? 가족들 다 너 때문에 스트레스 받아.",
                "내가 너한테 쏟은 돈이 얼마인데? 그것도 모르고 이렇게 배은망덕하니?",
                "너 같은 자식 둔 게 내 인생 최대의 실수야.",
                "옆집 애는 부모한테 얼마나 잘하는데. 너는 왜 그것도 못하니?",
                "네가 우리 가족의 수치야. 밖에 나가서 네가 우리 자식이라고 말하기도 부끄러워.",
                "너 하나 때문에 온 가족이 불행해. 이게 다 네 탓이야."
            };

            for (String content : familyStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(family);
                statementRepo.save(stmt);
            }

            System.out.println("=== 초기 데이터 생성 완료 ===");
            System.out.println("Situations: " + situationRepo.count());
            System.out.println("Statements: " + statementRepo.count());
        };
    }
}