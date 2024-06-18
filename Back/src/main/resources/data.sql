
INSERT INTO users(id,`user_email`,`user_name`, `user_password`, `resign`)
VALUES(1,"admin@admin.com" , "admin" , "$2a$10$FJJqIgvfeCYuU4h9SW.Ac.Sqrxt4FDztE33sYA5e62gqGput6QceK" , false);

INSERT INTO `user_roles`(`user_id`,roles) VALUES(1,"ROLE_ADMIN");

INSERT INTO users(resign, id, address, detailed_address, phone_number, post_code, user_email, user_name, user_password)
VALUES(false, 2, '제주특별자치도 제주시 영평동 2181', '111동 111호', '01012345678', '63309', 'rhkdgh930@naver.com', '명광호', '$2a$10$hlR3wBkjlD8cdeDKffqNeuGownEvM1qvzTUlMGmEtcJ4Hd.AwcmB6'
);

INSERT INTO users(id, address, detailed_address, phone_number, post_code, user_email, user_name, user_password, resign)
VALUES(3, '서울시 종로 청와대로 1길', '파란지붕', '01012345678', '12345', '1257312@naver.com', '우문식', '$2a$10$k81xscRbABX7FRpVr5nBw.eboFvzpKr9FEOWYt98v7DlatM5pzBZy', false);

INSERT INTO users
VALUES(false, 4, '청주', '용암동', '0101234567', '12345', 'tmdvy@naver.com', '홍승표', "$2a$10$FJJqIgvfeCYuU4h9SW.Ac.Sqrxt4FDztE33sYA5e62gqGput6QceK"
);

INSERT INTO user_roles(user_id, roles)
VALUES('3', 'USER');

INSERT INTO category(name) VALUES ("건강/취미");
INSERT INTO category(name) VALUES ("경제경영");
INSERT INTO category(name) VALUES ("공무원 수험서");
INSERT INTO category(name) VALUES ("과학");
INSERT INTO category(name) VALUES ("달력/기타");
INSERT INTO category(name) VALUES ("대학교재");
INSERT INTO category(name) VALUES ("만화");
INSERT INTO category(name) VALUES ("사회과학");
INSERT INTO category(name) VALUES ("소설/시/희곡");
INSERT INTO category(name) VALUES ("수험서/자격증");
INSERT INTO category(name) VALUES ("어린이");
INSERT INTO category(name) VALUES ("에세이");
INSERT INTO category(name) VALUES ("여행");
INSERT INTO category(name) VALUES ("역사");
INSERT INTO category(name) VALUES ("예술/대중문화");
INSERT INTO category(name) VALUES ("외국어");
INSERT INTO category(name) VALUES ("요리/살림");
INSERT INTO category(name) VALUES ("유아");
INSERT INTO category(name) VALUES ("인문학");
INSERT INTO category(name) VALUES ("자기계발");
INSERT INTO category(name) VALUES ("잡지");
INSERT INTO category(name) VALUES ("장르소설");
INSERT INTO category(name) VALUES ("전집/중고전집");
INSERT INTO category(name) VALUES ("종교/역학");
INSERT INTO category(name) VALUES ("좋은부모");
INSERT INTO category(name) VALUES ("청소년");
INSERT INTO category(name) VALUES ("컴퓨터/모바일");
INSERT INTO category(name) VALUES ("초등학교참고서");
INSERT INTO category(name) VALUES ("중학교참고서");
INSERT INTO category(name) VALUES ("고등학교참고서");

INSERT INTO `book_sales` VALUES (23,45,12,27,1,1,'최신 JAVA 21 버전 반영!\n9년 동안 꾸준히 사랑받은 자바 베스트셀러, 『이것이 자바다』 3판!\n\n2015년 초판이 발간된 이후 『이것이 자바다』는 명실상부한 자바의 교과서, 자바의 바이블로 큰 사랑을 받아왔다. 기본에 충실하면서도 개념 하나하나를 놓치지 않는 저자의 꼼꼼한 설명은 많은 독자를 훌륭한 개발자로 성장하게 했다. 이번에 개정된 『이것이 자바다(3판)』은 최신 자바 21 LTS 버전을 기반으로 기존 내용을 보강했으며, 자바 LTS 버전별 경향과 특징을 반영하여 자바를 활용한 객체지향 프로그래밍의 모든 과정을 친절하게 안내한다.\n이 책은 자바 언어의 기초부터 다양한 자바 프로그래밍 기법까지 그림을 통해 체계적으로 설명한다. 또한 900개의 실전 예제를 따라 하면서 충분히 연습할 수 있도록 구성했다. 책을 완독하고 나면 코드만 보고도 자바의 구조와 실행 흐름을 머릿속으로 그려 내는 자신의 모습을 발견할 수 있을 것이다.','신용권^임경균','34200','https://shopping-phinf.pstatic.net/main_4667402/46674022625.20240402080417.jpg','9791169212298','https://search.shopping.naver.com/book/catalog/46674022625','20240401','한빛미디어','이것이 자바다 (교육 현장에서 가장 많이 쓰이는 JAVA 프로그래밍 기본서)')
,(12,12,11,27,2,1,'▶ 이 책은 자바를 다룬 이론서입니다. 자바의 기초적이고 전반적인 내용을 학습할 수 있도록 구성했습니다.','Horstmann, Cay S.','31500','https://shopping-phinf.pstatic.net/main_3246702/32467024674.20221019150629.jpg','9788964211830','https://search.shopping.naver.com/book/catalog/32467024674','20140303','한티미디어','자바 (Seventh Edition)')
,(15,7,10,27,3,1,'소프트웨어 개발을 잘하고 싶다면 ‘개발’ 공부를 해야 합니다!\n\n자바 개발자가 코틀린 같은 신생 언어를 다룰 수 있게 된다고 해서 개발을 더 잘하게 되는 것은 아닙니다. 소프트웨어 개발 능력을 키우고 싶다면 ‘개발’ 그 자체를 공부하고 기초적인 설계 원리를 이해할 수 있어야 합니다. 이 책은 객체지향, SOLID, 디자인 패턴, 테스트 같은 주요 개발 관련 이론이 실제 프로젝트에 어떻게 적용될 수 있는지 설명합니다.\n\n★ 이 책에서 다루는 내용 ★\n\n◎ 객체지향\n◎ SOLID\n◎ 스프링 안티 패턴\n◎ 스프링과 DDD\n◎ 레이어드 아키텍처\n◎ 헥사고날 아키텍처\n◎ 테스트가 필요한 이유\n◎ 테스트와 설계\n◎ 테스트 대역\n◎ TDD와 BDD','김우근','28800','https://shopping-phinf.pstatic.net/main_4807682/48076823621.20240531103620.jpg','9791158395155','https://search.shopping.naver.com/book/catalog/48076823621','20240611','위키북스','자바/스프링 개발자를 위한 실용주의 프로그래밍 (객체지향부터 스프링과 테스트까지, 다시 제대로 배우는 애플리케이션 개발)')
,(7,26,9,27,4,1,'프로그래밍 분야 8년 연속 베스트셀러!\n《Do it! 점프 투 파이썬》 전면 개정 2판 출시!\n\n중고등학생도, 비전공자도, 직장인도 프로그래밍에 눈뜨게 만든 바로 그 책이 전면 개정 2판으로 새롭게 태어났다! 챗GPT를 시작으로 펼쳐진 생성 AI 시대에 맞춰 설명과 예제를 다듬고, 최신 경향과 심화 내용을 보충했다. 또한 이번 개정 2판도 50만 코딩 유튜버인 조코딩과 협업을 통해 유튜브 동영상을 제공해 파이썬을 더 쉽게 공부할 수 있다.\n\n8년 연속 베스트셀러! 위키독스 누적 방문 300만! 독자의 입에서 입으로 전해진 추천과 수많은 대학 및 학원의 교재 채택을 통해 검증은 이미 끝났다. 코딩을 처음 배우는 중고등학생부터 코딩 소양을 기르려는 비전공자, 자기계발에 진심인 직장인까지! 이 책과 함께 파이썬 프로그래밍의 세계로 ‘점프’해 보자!','박응용','19800','https://shopping-phinf.pstatic.net/main_4035408/40354085633.20230927071024.jpg','9791163034735','https://search.shopping.naver.com/book/catalog/40354085633','20230615','이지스퍼블리싱','Do it! 점프 투 파이썬 (중학생도 첫날부터 실습하는 초고속 입문서)')
,(44,34,8,27,5,1,'혼자 해도 충분하다! 1:1 과외하듯 배우는 파이썬 프로그래밍 자습서\n\n『혼자 공부하는 파이썬』이 더욱 흥미있고 알찬 내용으로 개정되었습니다. 프로그래밍이 정말 처음인 입문자도 따라갈 수 있는 친절한 설명과 단계별 학습은 그대로! 혼자 공부하더라도 체계적으로 계획을 세워 학습할 수 있도록 ‘혼공 계획표’를 새롭게 추가했습니다. 또한 입문자가 자주 물어보는 질문과 오류 해결 방법을 적재적소에 배치하여 예상치 못한 문제에 부딪혀도 좌절하지 않고 끝까지 완독할 수 있도록 도와줍니다. 단순한 문법 암기와 코딩 따라하기에 지쳤다면, 새로운 혼공파와 함께 ‘누적 예제’와 ‘도전 문제’로 프로그래밍의 신세계를 경험해 보세요! 배운 내용을 씹고 뜯고 맛보고 즐기다 보면 응용력은 물론 알고리즘 사고력까지 키워 코딩 실력이 쑥쑥 성장할 것입니다.\n\n이 책은 독학으로 파이썬을 배우는 입문자가 ‘꼭 필요한 내용을 제대로 학습’할 수 있도록 구성했습니다. 뭘 모르는지조차 모르는 입문자의 막연한 마음에 십분 공감하여 과외 선생님이 알려주듯 친절하게, 핵심적인 내용만 콕콕 집어줍니다. 책의 첫 페이지를 펼쳐서 마지막 페이지를 덮을 때까지, 혼자서도 충분히 파이썬을 배울 수 있다는 자신감과 확신이 계속될 것입니다!\n\n베타리더와 함께 입문자에게 맞는 난이도, 분량, 학습 요소 등을 적극 반영했습니다. 어려운 용어와 개념은 한 번 더 풀어쓰고, 복잡한 설명은 눈에 잘 들어오는 그림으로 풀어냈습니다. ‘혼자 공부해 본’ 여러 입문자의 초심과 눈높이가 책 곳곳에 반영된 것이 이 책의 가장 큰 장점입니다.','윤인성','19800','https://shopping-phinf.pstatic.net/main_3250760/32507605957.20230509170119.jpg','9791162245651','https://search.shopping.naver.com/book/catalog/32507605957','20220601','한빛미디어','혼자 공부하는 파이썬 (1:1 과외하듯 배우는 프로그래밍 자습서)')
,(32,36,7,27,6,1,'데이터 기반의 통찰력 있는 의사결정을 위한 인과추론,\n효율적인 영향력 분석을 통한 성공적인 비즈니스 정책 결정\n\n온라인 마케팅 예산을 1달러 높이면 구매자는 얼마나 늘어날까요? 할인 쿠폰을 받아야만 구매하는 고객은 어떻게 알아낼까요? 최적의 가격 책정 전략은 어떻게 수립할 수 있을까요? 이 질문들처럼 인과추론은 여러분이 관리하는 요소들이 원하는 비즈니스 지표에 어떤 영향을 미치는지 파악하는 데 가장 유용한 방법입니다. 인과추론은 단 몇 줄의 파이썬 코드만으로 쉽게 구현할 수 있습니다.\n \n이 책은 영향력과 효과를 추정하는 데 있어 인과추론이 지닌 아직 활용되지 않은 잠재력을 설명합니다. 관리자, 데이터 과학자, 데이터 분석가를 위한 A/B 테스트, 선형회귀, 성향점수, 통제집단합성법, 이중차분법과 같은 고전적 인과추론 방법과, 이질적 효과 추정을 위한 머신러닝 도입과 같은 현대적 접근법을 실제 응용 사례와 함께 다룹니다.','마테우스 파쿠레','34200','https://shopping-phinf.pstatic.net/main_4604469/46044690673.20240227095458.jpg','9791169212113','https://search.shopping.naver.com/book/catalog/46044690673','20240305','한빛미디어','실무로 통하는 인과추론 with 파이썬 (데이터 분석에서 정책 수립까지, 이론과 사례 연구를 통한 실용적인 학습법)')
,(12,12,6,27,7,1,'간단한 코딩으로\n자바스크립트 실력을 빌드업하자!\n\n이 책은 웹 개발을 처음 시작하거나 관심이 있는 모두를 대상으로 쓰여졌습니다. 웹페이지의 효율적인 작성을 위해 웹 표준의 이해와 설명을 시작으로 책의 핵심 내용인 자바스크립트의 쓰임과 활용 그리고 다양한 응용과 기술적인 트렌드까지 살펴볼 수 있도록 구성하였습니다. \n\n또한, 웹페이지 작성을 위해 넓은 범위에서 전략적인 이해와 기술적인 세밀한 이야기를 함께 담으려고 노력하였습니다. 특히 핵심이 되는 용어는 가급적 쉽게 풀어 바로 이해될 수 있도록 하였습니다. \n\n저자 깃허브 : https://github.com/kwakmoonki/booksr/','곽문기','28800','https://shopping-phinf.pstatic.net/main_4534926/45349267640.20240120153715.jpg','9791192932477','https://search.shopping.naver.com/book/catalog/45349267640','20240125','생능북스','자바스크립트 (최고의 강의를 책으로 만나다)')
,(4,56,5,27,8,1,'코딩을 몰라도 걱정 제로, 이 책 하나로 충분히 웹 개발을 시작할 수 있다!\n\n이 책은 코딩 초보자가 문법을 빠르고 재밌게 배울 수 있도록 실무에서 주로 사용하는 내용을 쏙쏙 골라 다양한 예제와 함께 다룹니다. 개발 환경 설정부터 HTML, CSS, 자바스크립트 기초까지 한 권에 담았고, 마지막에는 실무에서 유용하게 활용할 수 있는 나만의 포트폴리오 페이지를 만들어 배운 내용을 완성합니다. 단순한 코딩 및 결과 확인식 설명에서 벗어나 원리를 이해하며 학습할 수 있어서 외우지 않아도 자연스럽게 이해되며, 베타 학습단과 함께 내용을 검증해 초보자 눈높이에 맞춰 설명하므로 코딩 초보자도 비전공자도 충분히 웹 개발에 입문할 수 있습니다.','김기수','23580','https://shopping-phinf.pstatic.net/main_3246297/32462974669.20230912084525.jpg','9791165219468','https://search.shopping.naver.com/book/catalog/32462974669','20220425','길벗','코딩 자율학습 HTML + CSS + 자바스크립트 (기초부터 반응형 웹까지 초보자를 위한 웹 개발 입문서)')
,(51,6,4,27,9,1,'혼자 해도 충분하다! 1:1 과외하듯 배우는 C 프로그래밍 자습서\n\n『혼자 공부하는 C 언어』가 더욱 흥미 있고 알찬 내용으로 돌아왔습니다. 혼자 공부하더라도 막히는 부분이 없도록 부가 설명용 동영상 ‘QR 코드’를 추가했습니다. 또한 최신 프로그램에서도 무리 없이 실습을 진행할 수 있도록 비주얼 스튜디오 2022 버전을 반영했습니다.\n\n물론, 프로그래밍이 정말 처음인 사람에게 ‘꼭 필요한 내용’과 뭘 모르는지조차 모르는 마음에 십분 공감해 과외 선생님이 알려주듯 핵심 내용만 콕콕 짚어 주는 ‘친절한 설명’, 누구나 쉽게 따라 할 수 있도록 구성된 ‘단계별 학습’ 그리고 입문자에게 맞는 난이도, 분량, 학습 요소 등을 ‘베타리더의 의견’에 따라 적극 반영한 건 여전합니다!\n\n단순한 문법 암기와 코딩 따라하기에 지쳤다면 새롭게 돌아온 친절한 ‘혼공 씨’와 함께 C 언어라는, 프로그래밍 언어의 근본을 경험해 보세요. 책의 첫 페이지를 펼치고 마지막 페이지를 덮을 때까지, 혼자서도 충분히 C 언어를 배울 수 있다는 자신감과 확신이 계속 들 것입니다!','서현우','23400','https://shopping-phinf.pstatic.net/main_4008232/40082328632.20230822102552.jpg','9791169210911','https://search.shopping.naver.com/book/catalog/40082328632','20230518','한빛미디어','혼자 공부하는 C 언어 (1:1 과외하듯 배우는 프로그래밍 자습서)')
,(24,30,3,27,10,1,'자바 ORM 표준 JPA는 SQL 작성 없이 객체를 데이터베이스에 직접 저장할 수 있게 도와주고, 객체와 관계형 데이터베이스의 차이도 중간에서 해결해준다. 이 책은 JPA 기초 이론과 핵심 원리, 그리고 실무에 필요한 성능 최적화 방법까지 JPA에 대한 모든 것을 다룬다. 또한, 스프링 프레임워크와 JPA를 함께 사용하는 방법을 설명하고, 스프링 데이터 JPA, QueryDSL 같은 혁신적인 오픈 소스를 활용해서 자바 웹 애플리케이션을 효과적으로 개발하는 방법을 다룬다.\n\n다음 링크에서 온라인 강의를 수강할 수 있다.\n\n■ 강의 링크: https://www.inflearn.com/roadmaps/149\n■ 온라인 강의 목록\n-자바 ORM 표준 JPA 프로그래밍 - 기본편: https://www.inflearn.com/course/ORM-JPA-Basic\n-실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발: https://www.inflearn.com/course/스프링부트-JPA-활용-1\n-실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화: https://www.inflearn.com/course/스프링부트-JPA-API개발-성능최적화#\n-실전! 스프링 데이터 JPA: https://www.inflearn.com/course/스프링-데이터-JPA-실전\n-실전! Querydsl: https://www.inflearn.com/course/Querydsl-실전','김영한','28800','https://shopping-phinf.pstatic.net/main_3243600/32436007738.20221229072907.jpg','9788960777330','https://search.shopping.naver.com/book/catalog/32436007738','20150728','에이콘출판','자바 ORM 표준 JPA 프로그래밍 (스프링 데이터 예제 프로젝트로 배우는 전자정부 표준 데이터베이스 프레임워크)')
,(32,45,2,27,11,1,'자바를 공부하는 것은 다양한 애플리케이션 개발을 위한 필수적인 과정이다!\n웹, 모바일, AI 등 다양한 분야에 필수적인 객체 지향 언어 자바. 이제 체계적으로 배우자!\n\n웹에 강한 언어인 자바는 모바일뿐만 아니라 빅 데이터 처리나 AI 등의 신기술 영역에서도 사용되어 갈수록 활용률이 늘어나고 있습니다. 자바 언어는 오랜 시간이 지나도 그 위용이 떨어지지 않습니다.\n\n이 책은 20년 이상 자바를 강의해 온 저자가 자바 언어를 처음 접하는 학습자들에게 적합하도록 집필했으며, 자바 언어를 다루는 방법을 상세하면서도 복잡하지 않은 설명을 통해 알려 줍니다. 자바를 완전히 처음 접하는 입문자, 공부하다가 그만두었거나 공부한 지 오래된 재학습자, 꼭 필요한 부분만 빠르게 훑어보고 싶은 실무자까지 이 책을 통해 자바를 체계적으로 배울 수 있습니다.\n\n이 책의 모든 내용은 최신 버전인 자바 21에 맞춰 제작되었으며, 그대로 따라 해 볼 수 있는 실전 예제 코드를 제공합니다.','오정원','27900','https://shopping-phinf.pstatic.net/main_4701623/47016238619.20240414071131.jpg','9791167640659','https://search.shopping.naver.com/book/catalog/47016238619','20240405','혜지원','자바 프로그래밍 (실전 예제로 기초부터 탄탄히 배우는)')
,(43,15,1,27,12,1,'『2025 이유진 국어 독해 알고리즘 딥러닝』은 『스키마』, 『코어』, 『딥러닝』으로 구성된 2025 이유진 국어 독해 알고리즘 시리즈 중 가장 고난도의 문제를 수록하고 있는 9·7급 공무원 국어 시험 대비 기본서이다.\n\n『독해 알고리즘 코어』로 방법론과 문제 유형을 익힌 후 퀄리티 높은 문제들을 풀어보며 문제 해결 능력을 키울 수 있도록 『독해 알고리즘 코어』와 동일한 세부 목차로 내용을 구성하였다.\n\n인사혁신처 출제 기조 전환에 따른 문제 유형을 쉽게 파악하고 효율적으로 학습할 수 있는 본서의 세부 내용은 아래와 같다. \n\n\nPART 2. 심화 트레이닝\n문제 풀이 방법론에 맞춰 훈련할 수 있도록, 패턴별로 방법론과 심화 트레이닝 문제들을 수록하였다. 수록된 문제를 통해 문제를 해결하는 연습을 하며 심화 문제 해결 능력을 기를 수 있다.\n\nPART 3. 딥러닝\n‘언어학 추론, 논리 추론, 문학 추론, 단일 지문 2문항’ 유형의 심화 문제를 모아 제시하여, 지문을 빠르게 분석하여 정답을 찾는 훈련이 가능하도록 하고 있다.\n\n정답 및 해설에는 문제를 풀어가는 과정에 대한 설명을 상세하게 수록하여, 문제 유형을 분석하고 문제 풀이 능력을 보다 쉽게 습득할 수 있도록 하였다.','이유진','16200','https://shopping-phinf.pstatic.net/main_4802854/48028540620.20240529092803.jpg','9791167226686','https://search.shopping.naver.com/book/catalog/48028540620','20240603','메가스터디교육','2025 이유진 국어 독해 알고리즘 딥러닝 (9·7급 공무원 시험 대비)');