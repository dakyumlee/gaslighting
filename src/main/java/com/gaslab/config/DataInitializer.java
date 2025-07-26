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
                "네가 날 의심하니까 내가 그런 생각을 하게 되는 거야. 다 네 탓이야.",
                "너 진짜 매력 없다. 내가 아니면 누가 너 좋아하겠어?",
                "네가 살 찐 거 보고도 사랑한다고 말해주는 사람이 나밖에 더 있을까?",
                "너네 가족들도 너 싫어하잖아. 내가 유일하게 너 편이야.",
                "다른 여자들은 다 해주는데 너만 안 해주네. 진짜 이기적이야.",
                "내가 바람피운 게 아니라 너가 나를 외롭게 만들어서 그런 거야.",
                "네가 날 화나게 만들었어. 내가 때린 게 아니라 네가 맞을 짓을 한 거야.",
                "너 때문에 내가 술을 마시는 거야. 네가 나를 미치게 만들어.",
                "내 전 여친/남친은 이런 거 다 해줬는데. 너는 왜 못해?",
                "네가 일하는 것도 내가 허락해서 하는 거야. 감사할 줄 알아야지.",
                "친구들 만나지 마. 다들 너한테 나쁜 영향만 줘.",
                "네 옷차림이 문제야. 그렇게 입고 다니니까 사람들이 이상하게 봐.",
                "내가 네 휴대폰 보는 게 뭐가 문제야? 숨길 게 없으면 보여줄 수 있잖아.",
                "너 없으면 난 죽을 거야. 네가 나를 버리면 자살할 거야.",
                "사랑하면 이 정도는 참아줘야지. 너는 날 사랑하지 않나봐.",
                "네가 나한테 이렇게 대하니까 내가 다른 사람한테 관심이 가는 거야.",
                "너는 항상 부정적이야. 그래서 우리 사이가 안 좋은 거야.",
                "내가 하는 말은 다 널 위한 거야. 왜 그걸 모르니?",
                "네가 예뻐지려고 노력하지 않으니까 내가 다른 여자를 보는 거야.",
                "돈도 제대로 못 벌면서 뭘 그렇게 잘났다고 까불어?",
                "너는 나 없으면 아무것도 못해. 내가 다 챙겨줘야 하잖아."
            };

            for (String content : relationshipStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(relationship);
                statementRepo.save(stmt);
            }

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
                "팀워크를 망치는 건 바로 너야. 다들 너 때문에 힘들어해.",
                "신입이 뭘 안다고 의견을 내? 10년은 더 배워야 할 판에.",
                "야근은 당연한 거야. 요즘 애들은 근성이 없어.",
                "내가 신입 때는 이런 건 기본이었어. 요즘 애들은 버릇이 없어.",
                "여자라서/남자라서 이런 일은 못하지? 역시 성별의 한계야.",
                "네가 실수한 거 내가 다 뒤처리했어. 나한테 빚진 거 알지?",
                "승진 못하는 건 네 실력이 부족해서야. 남 탓하지 마.",
                "회식 빠지면 팀워크 해치는 거야. 개인 사정은 핑계고.",
                "임신했다고 일을 대충하면 되나? 프로의식이 없네.",
                "군대 안 갔다 왔으니까 이런 것도 못 버티지. 정신력이 약해.",
                "네 나이에 이 정도 실력이면 이직은 꿈도 꾸지 마.",
                "내가 널 키워줬는데 이제 와서 퇴사한다고? 배신자.",
                "너 때문에 우리 부서 실적이 꼴찌야. 책임감이 있어?",
                "집안 사정? 회사가 네 집안 사정까지 봐줘야 해?",
                "연차 쓰는 것도 눈치 봐서 써야지. 네가 없으면 일이 안 돌아가.",
                "내가 하라는 대로만 해. 생각은 내가 할 테니까.",
                "네가 무슨 전문가야? 그냥 시키는 일이나 해.",
                "다른 부서 사람들이 너 욕하던데? 소문 안 좋아.",
                "내가 아니었으면 너 진작 잘렸어. 고마운 줄 알아야지.",
                "일도 못하면서 똑똑한 척은. 겸손이 없어.",
                "이런 간단한 것도 이해 못하면 뭘 할 수 있겠어?"
            };

            for (String content : workplaceStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(workplace);
                statementRepo.save(stmt);
            }

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
                "너 하나 때문에 온 가족이 불행해. 이게 다 네 탓이야.",
                "네가 못나서 우리 집이 이 모양이야. 잘난 자식이었으면 달랐겠지.",
                "공부도 못하고, 운동도 못하고, 뭐 하나 잘하는 게 있니?",
                "결혼도 못하고 이 나이에 집에 얹혀살면서 부끄럽지도 않니?",
                "네가 낳은 자식도 너처럼 못났더라. 역시 피는 못 속여.",
                "우리가 너 때문에 이혼할 뻔했어. 네가 우리 부부사이를 망쳤어.",
                "너는 왜 태어났니? 차라리 없었으면 좋았을 텐데.",
                "네가 아프면서부터 집안이 망했어. 다 네 탓이야.",
                "형제들은 다 성공했는데 너만 이 꼴이야. 집안의 짐덩어리.",
                "내가 너 대학 보내려고 얼마나 고생했는데 겨우 이 정도야?",
                "시집/장가도 못 가고 뭐하는 거니? 동네 망신이야.",
                "너는 평생 우리한테 신세나 지고 살 거야. 독립은 꿈도 꾸지 마.",
                "네가 정상이 아니야. 우리 집안에 이런 사람은 없었어.",
                "너 같은 자식은 없는 게 나아. 입양 보낼 걸 그랬어.",
                "네가 뭘 안다고 말대꾸야? 부모한테 이러는 게 사람이니?",
                "우리가 널 위해 희생한 게 얼마인데? 평생 갚아도 못 갚아.",
                "너는 실패작이야. 다시 키운다면 절대 안 낳을 거야.",
                "집안 분위기 망치는 건 항상 너야. 네가 없으면 화목할 텐데.",
                "부모 재산 노리고 있지? 한 푼도 안 줄 거야.",
                "네가 우리한테 해준 게 뭐가 있다고 큰소리야?",
                "평생 부모 속만 썩이고 살 거니? 차라리 죽는 게 효도야."
            };

            for (String content : familyStatements) {
                Statement stmt = new Statement();
                stmt.setContent(content);
                stmt.setSituation(family);
                statementRepo.save(stmt);
            }

            System.out.println("=== 강화된 초기 데이터 생성 완료 ===");
            System.out.println("Situations: " + situationRepo.count());
            System.out.println("Statements: " + statementRepo.count());
            System.out.println("- 연인관계: 30개");
            System.out.println("- 직장: 30개");
            System.out.println("- 가족: 30개");
            System.out.println("총 90개의 가스라이팅 패턴 등록");
        };
    }
}