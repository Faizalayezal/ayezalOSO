package com.sanxingrenge.benben.screens.zodiacSignModule

import androidx.lifecycle.MutableLiveData
import com.sanxingrenge.benben.R
import com.sanxingrenge.benben.constant.ChatMessageObject
import com.sanxingrenge.benben.constant.FileData
import com.sanxingrenge.benben.responseModel.*
import java.util.ArrayList

/*data class NormalZodiacData(
    val title: String, val smallImage: Int, val bigImage: Int,
    val bigImageDescription: String, val rulingPlanet: PlanetType, val element: ElementType,
    val bestMatch: List<Int>, val keyword: String, val keyTrait: String,
    val luckyNo: String, val luckyJewelry: List<JewelryType>, val luckyColor: String,
    val occupation: String, val advantage: String, val weakness: String,
)*/

/*sealed class ElementType(val img: Int, val name: String) {
    class ElementFire : ElementType(R.drawable.element_fire, "Fire")
    class ElementEarth : ElementType(R.drawable.element_earth, "Earth")
    class ElementAir : ElementType(R.drawable.element_air, "Air")
    class ElementWater : ElementType(R.drawable.element_water, "Water")
}*/

/*sealed class JewelryType(val img: Int, val name: String) {
    class JewelryAmber : JewelryType(R.drawable.jewel_amber, "Amber")
    class JewelrySapphire : JewelryType(R.drawable.jewel_sapphire, "Sapphire")
    class JewelryCoral : JewelryType(R.drawable.jewel_coral, "Coral")
    class JewelryJasper : JewelryType(R.drawable.jewel_jasper, "Jasper")
    class JewelryBlackCrystal : JewelryType(R.drawable.jewel_black_crystal, "Black Crystal")
    class JewelryAmethyst : JewelryType(R.drawable.jewel_amethyst, "Amethyst")
    class JewelryBlackJade : JewelryType(R.drawable.jewel_black_jade, "Black Jade")
    class JewelryBlackPearl : JewelryType(R.drawable.jewel_black_pearl, "Black Pearl")
    class JewelryIvory : JewelryType(R.drawable.jewel_ivory, "Ivory")
    class JewelryRuby : JewelryType(R.drawable.jewel_ruby, "Ruby")
    class JewelryJade : JewelryType(R.drawable.element_jade, "Jade")
    class JewelryOpal : JewelryType(R.drawable.jewel_opal, "Opal")
    class JewelryPearl : JewelryType(R.drawable.jewel_pearl, "Pearl")
}*/

/*sealed class PlanetType(val img: Int, val name: String) {
    class PlanetSun : PlanetType(R.drawable.planet_sun, "The Sun")
    class PlanetMercury : PlanetType(R.drawable.planet_mercury, "Mercury")
    class PlanetVenus : PlanetType(R.drawable.planet_venus, "Venus")
    class PlanetPluto : PlanetType(R.drawable.planet_pluto, "Pluto")
    class PlanetJupiter : PlanetType(R.drawable.planet_jupiter, "Jupiter")
    class PlanetSaturn : PlanetType(R.drawable.planet_saturn, "Saturn")
    class PlanetUranus : PlanetType(R.drawable.planet_uranus, "Uranus")
    class PlanetNeptune : PlanetType(R.drawable.planet_neptune, "Neptune")
    class PlanetMars : PlanetType(R.drawable.planet_mars, "Mars")
    class PlanetMoon : PlanetType(R.drawable.planet_moon, "Moon")
}*/

/*val normalZodiacMainList = listOf(
    NormalZodiacData(
        title = "Leo",
        smallImage = R.drawable.symbol_leo,
        bigImage = R.drawable.big_leo,
        bigImageDescription = "Leo 7.24-8.23 at the 5th Palace",
        rulingPlanet = PlanetType.PlanetSun(),
        element = ElementType.ElementFire(),
        bestMatch = listOf(
            R.drawable.symbol_aries,
            R.drawable.symbol_libra,
            R.drawable.symbol_sagitarius,
            R.drawable.symbol_aquarius
        ),
        keyword = "Pleasure",
        keyTrait = "Brave to get the power",
        luckyNo = "19",
        luckyJewelry = listOf(JewelryType.JewelryAmber()),
        luckyColor = "Red, Yellow, Green",
        occupation = "Politician, entertainer, service industry, salesman, educator, military, transportation industry, etc. If you start your own business, it is best to engage in a business that deals with cash. If you work in a company, it is best to engage in business, planning, publicity, and trade. It is also good to be a secretary and overseas stationed staff, but it’s best to start a business on your own.",
        advantage = "Loving, enthusiastic, generous, leading, generous, open-minded, innovative, and talented in drama and performance.",
        weakness = "Subjective consciousness is too strong, self-righteous, intolerant, intolerant of opinions that are at odds with oneself, self-esteem, snobbery, excessive desire for power, bullying, and nosy.\nReality feels dull, and it is easy to float under the sugar-coated cannonballs of others, such as flattery and praise, not knowing where you are, especially if you are not immune to the wonderful atmosphere, you should pay attention to it. I adore vanity and like extravagance and waste, so try not to carry cash. The money earned before the age of 30 cannot be retained. Sometimes I will fall into loneliness and experience painful inner struggles. If I try to get rid of loneliness and interact with the opposite sex, I will definitely fail.",
    ),
    NormalZodiacData(
        title = "Virgo",
        smallImage = R.drawable.symbol_virgo,
        bigImage = R.drawable.big_virgo,
        bigImageDescription = "Virgo 8.24-9.23 at the 6th Palace (House of change)",
        rulingPlanet = PlanetType.PlanetMercury(),
        element = ElementType.ElementEarth(),
        bestMatch = listOf(
            R.drawable.symbol_taurus,
            R.drawable.symbol_scorpio,
            R.drawable.symbol_capricon,
            R.drawable.symbol_pisces
        ),
        keyword = "Perfect",
        keyTrait = "Analytical power",
        luckyNo = "7",
        luckyJewelry = listOf(
            JewelryType.JewelryAmber(),
            JewelryType.JewelrySapphire()
        ),
        luckyColor = "Grey",
        occupation = "Financial professionals, civil servants, accountants, teachers, musicians, writers, architects, engineers, service industries, lawyers, public welfare entrepreneurs, etc. If you work in a company, it is suitable for general affairs, labor, planning, research, production, secretarial and other positions. If you want to be more successful, you must master some foreign languages and professional knowledge.",
        advantage = "Modest, like tidy, careful, clear-headed and strong analytical skills, able to distinguish right from wrong.",
        weakness = "Like to pick bones in the egg, the requirements are too high to be faulty, sentimental, fussy, rigid, and hard to please.\nAs a Virgo, you lack humility, strong self-awareness, lack of gentle language, frank and straightforward, and blunt emotional expression, which is easy to be misunderstood. For you, lying, concealing money or having an affair will bring great losses, so everything should be fair and honest. It is not suitable for speculation and is prone to diseases, so you should pay special attention to your health, live regularly, and arrange your time reasonably.",
    ),
    NormalZodiacData(
        title = "Libra",
        smallImage = R.drawable.symbol_libra,
        bigImage = R.drawable.big_libra,
        bigImageDescription = "Libra 9.24-10.23 at the 7th Palace",
        rulingPlanet = PlanetType.PlanetVenus(),
        element = ElementType.ElementAir(),
        bestMatch = listOf(
            R.drawable.symbol_gemini,
            R.drawable.symbol_leo,
            R.drawable.symbol_sagitarius,
            R.drawable.symbol_aquarius
        ),
        keyword = "Partnership",
        keyTrait = "Hard to make choice",
        luckyNo = "3",
        luckyJewelry = listOf(
            JewelryType.JewelryCoral(),
            JewelryType.JewelryAmber()
        ),
        luckyColor = "Brown",
        occupation = "Diplomats, writers, artists, designers, doctors, legal experts, tax accountants, and are also suitable for tourism and service industries. If you work in a company, it is suitable for business, trade, general affairs and other positions. You will have luck once participating in various organizations and groups. If a husband and wife start a business together, they will be more successful.",
        advantage = "Fair and objective, with a sense of justice, strong adaptability, appreciative of beauty, strong logic, good analysis, natural elegance, romantic love master, diplomatic skills, adapt to circumstances, able to bend and stretch, strong adaptability.",
        weakness = "Indecisive, indecisive, unsteady, easy to be influenced by others, wishing to be homeless, afraid of offending people, unable to bear pressure, not taking responsibility, demanding too much fairness, not suffering from losses, restoring things to peace, treating symptoms but not the root cause, always justifying themselves, with too many excuses , Likes to enjoy, likes to rest and dislike work, often inadvertently discharges randomly, lacks the ability of introspection.",
    ),
    NormalZodiacData(
        title = "Scorpio",
        smallImage = R.drawable.symbol_scorpio,
        bigImage = R.drawable.big_scorpio,
        bigImageDescription = "Scorpio  (10.24-11.22) at the 8th Palace",
        rulingPlanet = PlanetType.PlanetPluto(),
        element = ElementType.ElementWater(),
        bestMatch = listOf(
            R.drawable.symbol_taurus,
            R.drawable.symbol_cancer,
            R.drawable.symbol_scorpio,
            R.drawable.symbol_pisces
        ),
        keyword = "Sex",
        keyTrait = "Desire",
        luckyNo = "7",
        luckyJewelry = listOf(
            JewelryType.JewelryJasper(),
            JewelryType.JewelryBlackCrystal()
        ),
        luckyColor = "Purple  Black",
        occupation = "Doctors, writers, politicians, judges, chemists, engineers, drivers, athletes, news media industry, travel service industry, etc. If you work in a company, it is suitable for secretary and other positions that play a role of adviser, or for publicity, planning, investigation, labor, shipping, construction, etc. It is best to seek long-term development in one place, then the god of luck will visit you.",
        advantage = "Foresight, clear grudges, keen intuition, ability to implement decisions, fearless of setbacks, persistence, loyalty to friends, natural sexy charm, persistence in pursuing the truth of things, good at keeping secrets, potential passion for life, Strategy, ability to understand the key points of things, emotionally sensitive, delicate feelings, rich imagination, decisive, practical and enthusiastic, strong subjective opinions, firm-willed, perseverance, agility, intelligence, high insight, and intuition.",
        weakness = "Too strong, possessive, jealous, jealous, suspicious, vindictive, unreasonable, emotional, knowingly guilty, duplicity, city government too deep, love and hatred too strong, impulsive, aggressive, irritable, Stubborn, stubborn, hard to control, suspicious, fickle, emotional, adventurous, often arrogant dreams, insidious and indifferent.",
    ),
    NormalZodiacData(
        title = "Sagittarius",
        smallImage = R.drawable.symbol_sagitarius,
        bigImage = R.drawable.big_sagitarius,
        bigImageDescription = "Sagittarius (11/22-12/21) at the 9th Palace",
        rulingPlanet = PlanetType.PlanetJupiter(),
        element = ElementType.ElementFire(),
        bestMatch = listOf(
            R.drawable.symbol_aries,
            R.drawable.symbol_gemini,
            R.drawable.symbol_leo,
            R.drawable.symbol_aquarius
        ),
        keyword = "Philosoph",
        keyTrait = "Freedom",
        luckyNo = "6",
        luckyJewelry = listOf(JewelryType.JewelryAmethyst()),
        luckyColor = "Light Blue",
        occupation = "Artists, diplomats, jurists, trade industry, sports industry, tourism, service industry and other occupations that require leadership and creativity. If you work in a company, you can choose planning, publicity, business, and foreign-related departments that have a sense of liveliness, a wide range, and a certain degree of discretion, such as business, competition, or production sites.",
        advantage = "Born to be optimistic, full of ideals in life, honest and frank, rich sense of humor, love for peace, friendly to others, strong in action, have his own philosophy of life, can withstand blows, enthusiasm to save the world, optimistic, lively, frank, self-esteem Strong, versatile, highly intelligent, intuitionistic, capable of inspiring others, open-minded, adaptable, good judgment, capable of handling urgent matters, good at philosophical thinking, and a lofty sense of justice , Very sensitive and intelligent, loves freedom, sincerity, integrity, reliability, and prudence.",
        weakness = "Careless, outspoken, easy to offend people, lack of patience, ignorance of the world, impulsive behavior, not knowing how to think twice, not believing in evil, not listening to advice, over-idealized, unrealistic, lack of step-by-step planning, too much emotion and anger Color, sensitive personality, a little impetuous, bragging, exaggerated, unresponsible, capricious, prone to restlessness, blindly and overly optimistic, careless, extreme, and a little clumsy.",
    ),
    NormalZodiacData(
        title = "Capricorn",
        smallImage = R.drawable.symbol_capricon,
        bigImage = R.drawable.big_capricon,
        bigImageDescription = "Capricorn 12.22-1.20 at the 10th Palace",
        rulingPlanet = PlanetType.PlanetSaturn(),
        element = ElementType.ElementEarth(),
        bestMatch = listOf(
            R.drawable.symbol_taurus,
            R.drawable.symbol_scorpio,
            R.drawable.symbol_virgo,
            R.drawable.symbol_pisces
        ),
        keyword = "Social status",
        keyTrait = "Actual",
        luckyNo = "4",
        luckyJewelry = listOf(JewelryType.JewelryBlackJade()),
        luckyColor = "Coffee black",
        occupation = "Politicians, legal experts, musicians, writers, athletes, and chemistry or health related industries.",
        advantage = "Have a practical outlook on life, work on the ground, strong willpower, not easily affected, cautious everywhere, have the perseverance to overcome difficulties, adhere to principles, value discipline, have a family concept, be humble with others, and have a unique sense of humor, Be conservative, prudent, practical, responsible, reliable, witty, independent, respect authority, value yourself, cautious, ambitious, patient, and disciplined.",
        weakness = "Too realistic, stubborn, not optimistic enough, personal egoism, lack of romantic interest, too suppressed one's desires, too focused on personal goals, lack of care and enthusiasm for the crowd, not good at communication, not adaptable, pessimistic, kind Change, indifferent and serious in appearance, not easy to approach, clingy, stingy.",
    ),
    NormalZodiacData(
        title = "Aquarius",
        smallImage = R.drawable.symbol_aquarius,
        bigImage = R.drawable.big_aquarius,
        bigImageDescription = "Aquarius 1.21-2.18    at the 11th Palace",
        rulingPlanet = PlanetType.PlanetUranus(),
        element = ElementType.ElementAir(),
        bestMatch = listOf(
            R.drawable.symbol_pisces,
            R.drawable.symbol_leo,
            R.drawable.symbol_libra,
            R.drawable.symbol_sagitarius
        ),
        keyword = "Friends",
        keyTrait = "Curiosity",
        luckyNo = "22",
        luckyJewelry = listOf(JewelryType.JewelryBlackPearl()),
        luckyColor = "Black",
        occupation = "Salespersons, engineers, designers, programmers, athletes, service industries, crews and other professions that highlight freedom and creativity. If you are working in a company, you should try to avoid shrinking inside the company, because contacting industry peers and outreaching with groups or meetings are more suitable for giving full play to your talents.",
        advantage = "Advocating freedom, full of humane spirit, wide-ranging interests, full of creativity, willing to discover the truth, meticulous, with rational wisdom, independence, personal style, willing to help others, loyal to one’s feelings, strong curiosity, independence, fraternity, friendly, loyal, reliable, creative, farsighted, wise, innovative, sharp-minded, observant, and full of renovational spirit.",
        weakness = "Lack of enthusiasm and romance, fetishizes personal freedom, too idealistic, do not follow the cards, believe in their own judgments, changeable thoughts, have no perseverance, hard to put their hearts on partner, overemphasize the autonomy of life, too sensible, weird personality, overly persistent, unconventional, easy to exaggerate, stubborn, clingy, unaware of flexibility, stubborn, some extreme, rebellious, and out of gregarious like an outcast\nThrough talent and hard work, Aquarius can develop smoothly until middle age, but because of their strong self-awareness, they are easy to be blamed or cause discord. you has a mild personality, but he may also get bad reviews. You may encounter a romance suddenly, or fall into a lonely emotion, unable to extricate himself.  Although the income is substantial, due to the relatively large expenses, it is often difficult to make ends meet. Therefore, we must take a rational attitude towards money.",
    ),
    NormalZodiacData(
        title = "Pisces",
        smallImage = R.drawable.symbol_pisces,
        bigImage = R.drawable.big_pisces,
        bigImageDescription = "Pisces 2/18-3/20 at the 12th Palace",
        rulingPlanet = PlanetType.PlanetNeptune(),
        element = ElementType.ElementWater(),
        bestMatch = listOf(
            R.drawable.symbol_taurus,
            R.drawable.symbol_cancer,
            R.drawable.symbol_scorpio,
            R.drawable.symbol_capricon
        ),
        keyword = "Subconscious",
        keyTrait = "Discriminate",
        luckyNo = "11",
        luckyJewelry = listOf(JewelryType.JewelryIvory()),
        luckyColor = "White",
        occupation = "Designers, health-care occupations, beauticians, art directors, educators, service industries, western goods stores and other small and medium-sized business operators. If you are engaged in artistic occupations or water-related occupations, or both occupations at the same time, there will be unexpected gains.\n" +
                "Pisces has weak on the concept of money, paying more and getting less, so you must be especially cautious in economic transactions. It is difficult for Pisces to exert their talents in group life or work subject to certain norms. Therefore, it is best to be your own boss, or become an expert in a certain field, try to choose a career that can surpass reality and immerse yourself in the romantic world, and avoid careers that deal with money all day long. It is the best choice to pursue a creative career and cultivate your ideal career.",
        advantage = "Emotional, kind-hearted, self-sacrificing, unselfish, imaginative, understanding, intuitive, tolerant, gentle and polite, easy to trust others, romantic, naive, pure, gentle and intuitive, humble, sensitive , compassionate, affectionate, refined, adaptable, kind, and versatile.",
        weakness = "Not realistic enough, too much fantasies, not enough risk awareness, too emotional, sentimental, unsteady, lacking the courage to face reality, easy to fall into depression and inextricable, easy to develop the habit of lying, not good at financial management, easy affected by the environment, lack of reason, emotional, careless, impetuous, lack of self-confidence, weak-willed, indecisive, lax, and impractical.\nAvoid procrastination and entanglement in heterosexual relationships. Because soft-hearted is easy to fall into temptation, at the same time it is difficult to stop the temptation of sensory pleasure. The psychology of love of luxury and vanity may lead to serious mistakes, so be extra careful. There is a risk of accidents such as theft, fire, and car accident, so you must take out damages insurance after getting married.",
    ),
    NormalZodiacData(
        title = "Aries",
        smallImage = R.drawable.symbol_aries,
        bigImage = R.drawable.big_aries,
        bigImageDescription = "Aries   3.21-4.20 at the 1st Palace",
        rulingPlanet = PlanetType.PlanetMars(),
        element = ElementType.ElementFire(),
        bestMatch = listOf(
            R.drawable.symbol_gemini,
            R.drawable.symbol_leo,
            R.drawable.symbol_libra,
            R.drawable.symbol_sagitarius
        ),
        keyword = "Own",
        keyTrait = "Control",
        luckyNo = "5",
        luckyJewelry = listOf(JewelryType.JewelryRuby()),
        luckyColor = "Red",
        occupation = "Professional soldiers, politicians, civil servants, news media industry, surgeons, athletes, entertainers, crew members, advertising personnel, engineers, etc. If you work in a company, it is best to choose a department with autonomy and pay attention to a good relationship with your superiors. Labor management, foreign trade, construction, sales, planning and other positions are all suitable.",
        advantage = "Love freedom deeply and don't like being suppressed by the outside world. Have the ambition and adventurous spirit, have the courage to try, have great energy, and will go all out once the goal is determined.",
        weakness = "Lack of patience, irritability, impulsiveness, selfishness, self-centeredness, bitterness, aggressiveness, carelessness sometimes.\nAries have strong personalities and have a self-controlled life, but because they are not flexible enough and are too self-centered, they may be respected by people. With strong mobility and competitiveness, you can quickly gain a reputation for yourself, but if you don’t abandon your self-centered habits, you will easily suffer setbacks halfway through. Appears to be cautious and composed, good at opening up the situation, but not good at managing the situation, reckless and easy to fail.",
    ),
    NormalZodiacData(
        title = "Taurus",
        smallImage = R.drawable.symbol_taurus,
        bigImage = R.drawable.big_taurus,
        bigImageDescription = "Taurus 4/19-5/20 at the 2nd Palace",
        rulingPlanet = PlanetType.PlanetVenus(),
        element = ElementType.ElementEarth(),
        bestMatch = listOf(
            R.drawable.symbol_gemini,
            R.drawable.symbol_leo,
            R.drawable.symbol_libra,
            R.drawable.symbol_sagitarius
        ),
        keyword = "Money",
        keyTrait = "Fortune",
        luckyNo = "6",
        luckyJewelry = listOf(JewelryType.JewelryJade()),
        luckyColor = "Pink",
        occupation = "Writer, painter, children’s writer, designer, food manufacturing, publishing industry, real estate agent, financial industry, etc. It is best to invest in a certain field for a long time. If you work in a company, you should use general affairs, labor, planning and other management positions and technology Research positions are a priority, and secretarial professions are also very promising.",
        advantage = "Independent, strong-willed, enthusiastic, friendly, patient and responsible, trustworthy, practical, reliable, business-minded and solid values, rich in beauty, like good food and exquisite and expensive luxury goods.",
        weakness = "Greedy, stubborn, jealous, possessive, lazy, old-fashioned, lack of resilience, self-indulgence, irritability.\nWherever you go, it is enough to make it shining, but sometimes it will cause disagreement because of disagreement, so it must be moderately tolerant. Don't just look at immediate interests. Only by learning to look at life with a long-term perspective can you achieve great cause. Saving and restraint are important, but if you are too stingy, even if you invest a lot of money, you won’t be able to gain a good reputation and you will only be disgusted by people.",
    ),
    NormalZodiacData(
        title = "Gemini",
        smallImage = R.drawable.symbol_gemini,
        bigImage = R.drawable.big_gemini,
        bigImageDescription = "Gemini 5.22-6.21 at the 3rd Palace",
        rulingPlanet = PlanetType.PlanetMercury(),
        element = ElementType.ElementAir(),
        bestMatch = listOf(
            R.drawable.symbol_gemini,
            R.drawable.symbol_leo,
            R.drawable.symbol_libra,
            R.drawable.symbol_sagitarius
        ),
        keyword = "Communication",
        keyTrait = "Thought",
        luckyNo = "7",
        luckyJewelry = listOf(JewelryType.JewelryOpal()),
        luckyColor = "Yellow",
        occupation = "Education industry, news industry, entertainer, diplomat, salesman, tourism service industry, designer, athlete, real estate, securities industry, etc. If you work in a company, you can choose companies that can display creativity and imagination, such as trade, advertising, planning, sales, insurance, and securities. Although Gemini person is clear-headed, talented, and slick in the world, it is difficult to achieve great results if he is always open-mouthed and not cautious.",
        advantage = "Adaptable, witty, agile, like busyness and change, active, lively and talkative, smart and versatile, have a genius in writing and language, have a keen sense of fashion, and can permanently maintain a young and fashionable appearance.",
        weakness = "Suspicious, fickle, dual personality, lack of patience, cunning, restless, and violent and chattering when the extraordinary energy fails to develop.\nAs a Gemini, you are talented and can be recognized by people, but due to your lack of modesty and competitive awareness, you may miss big things. It is best to cultivate your patience. When dealing with interpersonal relationships, don't just look at the immediate interests, but look forward to the future and make overall plans. Only in this way will you gradually form your own solid sphere of influence. Indecision, not having a clear goal is your biggest shortcoming, you should learn to lock the goal and move forward courageously.",
    ),
    NormalZodiacData(
        title = "Cancer",
        smallImage = R.drawable.symbol_cancer,
        bigImage = R.drawable.big_cancer,
        bigImageDescription = "Cancer 6.22-7.23 at the 4th Palace",
        rulingPlanet = PlanetType.PlanetMoon(),
        element = ElementType.ElementWater(),
        bestMatch = listOf(
            R.drawable.symbol_virgo,
            R.drawable.symbol_scorpio,
            R.drawable.symbol_capricon,
            R.drawable.symbol_pisces
        ),
        keyword = "Family",
        keyTrait = "Sensitive Fealing",
        luckyNo = "2",
        luckyJewelry = listOf(JewelryType.JewelryPearl()),
        luckyColor = "Green",
        occupation = "Catering industry, service industry, designer, daily necessities manufacturing, sales industry, writer, charity business, news media, entertainer, etc. If you work in a company, you are suitable for production and planning positions. If you are engaged in trade and management, you can only be recognized if you show sufficient resilience and enthusiasm.",
        advantage = "Kind, enthusiastic, sensitive, and compassionate; good at memory, sharp mind, good comprehension, good adaptability, high imagination; strong maternal or paternal instinct, strong protective color, cautious, thrifty; strong will Strength and endurance, perseverance; good financial concept; patriotism; loyal to love, value the warmth and stability of the family, good at housework, value the harmony of the family, is the most family concept of all constellations.",
        weakness = "Naturally suspicious and emotional to make it difficult to please, strong jealousy and have Oedipus complex, may be self-pity due to over-sensitivity, fickle personality, unstable, sometimes lost because of too serious attitude to life, boring, narrow-minded, harsh, Greedy, sloppy, and likes to be flattered.\nAs a Cancer, you have excellent learning ability and creative ability, but you lack the spirit of independent development, and sometimes you may even have the idea of embezzling others' ideas. In other words, they have a tendency to be lazy. Only by curbing this tendency and cultivating a sense of responsibility and participation in social life can they succeed. You have a clear mind and brilliant talents, but if you have the luxury of becoming famous in multiple fields at the same time, you will probably get nothing in the end. Keep this in mind.",
    ),
)
val normalZodiacSmallImageList = normalZodiacMainList.map { it.smallImage }*/

data class ChineseZodiacData(
    val title: String,
    val smallImage: Int,
    val bigImage: Int,
    val imageName: Int,
    val relationshipDescription: String,
    val advantages: List<String>,
    val disadvantages: List<String>,
)



var normalZodiacMainList: List<ConstellationDataResponse> = listOf()
var normalZodiacSmallImageList: List<String?> = listOf()
//var normalZodiacSmallImageList =normalZodiacMainList.map { it.icon}

var chineseZodiacMainList: List<ChinesZodicDataResponse> = listOf()
var chinesZodiacSmallImageList: List<String?> = listOf()


var RecommendnedMainList: List<DiscoverResponseData> = listOf()
var isEdit: List<DisEdit> = listOf()
var questionList: List<GetQuestionsListDataResponse> = listOf()
var personalityDataList: List<PersonalityDesignDataResponse> = listOf()
var imageDatas: List<FileData> = listOf()
var FriendMainList: List<FriendRequestDataResponse> = listOf()
//var listMessage: List<ChatMessageObject> = listOf()
var feedBackMainLList: List<GetFeedBackDataListResponse> = listOf()
var userMsgDataList: List<ConversionDataRsponse> = listOf()
//var listMessage2 = MutableLiveData<ArrayList<ChatMessageObject>>(ArrayList())



/*
val chineseZodiacMainList = listOf(
    ChineseZodiacData(
        title = "Dragon",
        smallImage = R.drawable.small_dragon,
        bigImage = R.drawable.big_dragon,
        imageName = R.drawable.name_dragon,
        relationshipDescription = "Have a high degree of sensitivity to color, full of talent, active pursuit of the object, exudes the charm, so that there are many suitors around, to fall in love or marry with them, their partner must have a big heart and special talents to maintain the affection, also praise them often. Dragon people are blessed with a lot of chances for love and marriage, but most of them are married late so the relationship is often erratic.",
        advantages = listOf(
            "Have a strong physique, energetic, full of vigor, have noble ideals, and a romantic trait, they like reputation and good face.",
            "Reluctant to admit defeat in everything, overconfident in doing things, strong self-awareness, ambitious goals, frank personality, strong leadership and strong fortune.",
            "Being honest and frank, there are seldom despicable hypocritical behaviors, and don't like gossip about others, not afraid of hardships, and want to be perfect in everything.",
            "They could be radical, very arrogant, and has a super-vulgar concept of being stronger than others.",
            "Women are generous, enthusiastic, passionate and considerate, they are the favorite partner of men in general.",
            "No matter do what work, they hate being instigated by others, so they like to plan things alone, and male dragon have some chauvinism.",
            "The dragon is a symbol of power and blessing, the proud son of heaven. Dragon people are told to be courageous, talented, generous, and energetic.",
        ),
        disadvantages = listOf(
            "Emotional instability, full of dreams, unpredictable, arrogant and lack of forgiveness.",
            "Unable to resist the gentle pink trap, there might have the countless colorful romances in a lifetime.",
            "It is rare for them to love others really, so they won’t be disappointed in love, but those who fall in love with them might drink their bitter wine.",
            "They often criticize others’ incapable for being unable to tolerate the poor others, because they have a perfectionist mentality.",
            "Outstanding talents can't help but be conceited sometimes. They can't withstand setbacks and tests. Once they I fail, they will run away. They lack of perseverance and resilience.",
        ),
    ),
    ChineseZodiacData(
        title = "Snake",
        smallImage = R.drawable.small_snake,
        bigImage = R.drawable.big_snake,
        imageName = R.drawable.name_snake,
        relationshipDescription = "When they first meet and associate with people, their indifferent personality will make people scared, but after a long time together, you will find that they are actually very passionate, have a fiery heart, are strict with women, and even picky, it is not easy for them to accept others. Women have a strong possessive desire, once they like men, they will never let him go. At the same time, they are very jealous they don’t allow their partner to make other friends, but they can’t restrain themselves. Therefore, men might have to pay attention on interacting with them.",
        advantages = listOf(
            "Have a mysterious, romantic, gentle appearance and a proficient attitude towards the world, personable and good at speech.",
            "Calm and composed, possess special talents, and have the fighting spirit to follow through.",
            "Do not show off their talents but move forward gradually according to the plan.",
            "Innately receptive and knowledgeable, caring for others with kindness, and strong resilience.",
            "They often gain exclusive opportunities, dream of creating a business with their own strength, but if they lack the spirit of cooperation, they are easy to fail.",
            "Taciturn and not easy to get angry, think twice about everything, is a smart intellectual.",
            "Understand their own abilities very well, attach to spiritual life, have the sixth sense of the next life and superhuman insight, and have a strong observation and judgment of things.",
            "Have been very lucky in financial throughout life, never lack of money, and have a strong desire for money.",
            "With a keen mind, although the nature is plain, they can be decisive and make quick decisions with brain and incredible inspiration."
        ),
        disadvantages = listOf(
            "Cold presence but possessive, with a soft side of personality, not easy to get close to or show sincerity, only interacting with others casually.",
            "Born to be vanity, often with a skeptical eye.",
            "Emotional instability, feelings are prone to twists and turns.",
            "Knowing the way to advance and retreat in social life, with a little jealousy, not easy to get along with people around them, emphasizing in feelings and money.",
            "Although the attitude is courteous, he is actually a stubborn person who refuses to admit defeat.",
            "Love is deep and dedicated, and can't tolerate the other party's grievances."
        ),

        ),
    ChineseZodiacData(
        title = "Horse",
        smallImage = R.drawable.small_horse,
        bigImage = R.drawable.big_horse,
        imageName = R.drawable.name_horse,
        relationshipDescription = "The way to fall in love does not like procrastinating or making ambiguous expressions. Because of the straightforward personality and clear of likes and dislikes, there are great restrictions when choosing a marriage partner. Women often get married late. If they get married early and don't respect their feelings, that would often lead to divorce. They must be given appropriate freedom when you interact with them, then two people can achieve harmony. Everything is simple for them, not procrastinated at all, they have strong emotion on love or hate, so they have the tendency to marry late. Women might be curious and committed to men and then regret dissatisfaction, that should be especially careful.",
        advantages = listOf(
            "Cheerful, romantic and enthusiastic, good at wording, and have a hearty and optimistic review of life.",
            "Heroism is very heavy and often fights injustices for others.",
            "Be proactive in doing things, have a disobedient temperament, everything can't last long, love is about outspoken and active, like to blow something, common friends are mostly in the distance, not closely.",
            "Free and unrestrained, not good at confidentiality, like to make friends around, get along well with others, love to take care of others kindly, and often open heart to others.",
            "The speech is very beautiful and has the ability to lead the masses  Because of the strong comprehension, he often knows the other's thoughts and trends before others have spoken.",
            "There are many ideas, there is a kind of latent consciousness of not admitting defeat, they will resist till the end even if they encounter setbacks once they decide to do things.",
            "Very talented in all aspects, with agile limbs.",
            "Horse people pay attention to the gorgeous clothes and always spend a lot of time in front of the mirror to have some make up or dressing up. ",
        ),
        disadvantages = listOf(
            "Strong with endurance, violent sometime, addicted to alcohol and gambling.",
            "Hate to take one's lonely work, they like the applause of the people and love the praise and worship of others.",
            "Strong subjective independence, does not accept other people's suggestions, likes to do whatever one wants, hates being restrained.",
            "For horse men, love is part of life; but for women, love is all their life.",
            "Blood type O-type horses like to fly around the rich and luxurious occasions, so they like to engage in eye-catching work. They get involved in everything and often give up on halfway.",
            "The least good at financial management, often only knows about increasing income but not throttling.",
            "Always have a good-faced attitude of admiring vanity."
        ),

        ),
    ChineseZodiacData(
        title = "Goat",
        smallImage = R.drawable.small_goat,
        bigImage = R.drawable.big_goat,
        imageName = R.drawable.name_goat,
        relationshipDescription = "The way of falling in love adopts a step-by-step way of expression, and will not give other a sense of oppression, just like the breeze blowing gently in the spring, soft and affectionate, they treat others in a respectful way. Sometimes it will make the others not understand what you are thinking? The other can't feel the heat of love, and that might cause breaking up finally, because falling in love requires sparks after all. Women are firm-willed, but on the other hand they rely on a strong mind and love to be coquettish. Women born in the goat year are cute and attractive, but lack the spirit of independence. Once they enter the family, they have neither time perspective nor good housekeeping that might make men concern for a good housewife.",
        advantages = listOf(
            "Be considerate in everything, deal with the surrounding affairs properly, enterprising, good at sociability, and gentle personality with a benevolent mind.",
            "Introverted and hardworking, with the characteristics of being soft on the outside and firm on the inside.",
            "Carefulness in doing things gives people a sense of reliability. They are sentimental, meticulous, and considerate.",
            "Go forward with great enthusiasm, have a lot of troubles throughout life, and take a variety of ways to deal with matters.",
            "“Kneel the suckling lamb” be filial all your life, have a strong endurance in doing things, and make unremitting progress.",
            "“Eat green on sight” which means sheep people works hard, but is subjective and stubborn, prefers mystery, mostly believes in ghosts and gods, and is a devout believer.",
            "Exuberant thirst for knowledge, even the trivial details are not overlooked.",
            "Inconvenient to waste money, know how to be frugal, treat people kindly, love nature, and have a noble and generous manner.",
            "Very popular and able to obtain the support of nobles to master the opportunities and develop the career.",
            "Women born in the Year of the Goat are kind-hearted and like to take care of others, usually a beauty with a good shape."
        ),
        disadvantages = listOf(
            "Sometimes pessimistic and indecisive, like resignation, dislike routine work.",
            "By nature, Sheep people loves being taken care of, praise from others and suggestions from friends.",
            "Don't dare to make a bold love confession.",
            "Very subjective and stubborn, but some weak and timid.",
            "Shyness is their characteristic, and very interested in peculiar and weird doctrines.",
            "Women born in the Year of the Goat usually pay attention to their beauty figure,  but doesn't like do the housework and be housewife."
        ),

        ),
    ChineseZodiacData(
        title = "Monkey",
        smallImage = R.drawable.small_monkey,
        bigImage = R.drawable.big_monkey,
        imageName = R.drawable.name_monkey,
        relationshipDescription = "They are also very smart in dating. They don’t like pedantic people. Who they pursue is perfect in their imagination. They hope that their future person is not only beautiful or handsome, but also a decent and generous person. The person at home is also a meek and considerate. In their hearts, they hope to meet someone who is as perfect as the lover of their dreams.",
        advantages = listOf(
            "Humor, wit, and lively, so many talents often surpass the crowd.",
            "Good popularity, but value fame and fortune, strong desire for monopoly, agile and self-confidence.",
            "Flexible hands and feet, good at imitating, open and generous.",
            "There are many friends of the opposite sex, enthusiasm and lack of constant, can becomes a love-only person once they met the true love.",
            "All things self-centered, good at communication with a lot of luck, gradually showing off when they are young, if they have perseverance, will have a great achievement.",
            "It is very strategic in handling things and never acts blindly."
        ),
        disadvantages = listOf(
            "Although he has extraordinary wisdom, but short-sighted, so they should sing less with high-profile, cherish more what they already have.",
            "A lot of ego, like to be picky.",
            "Too shrewd and a bit tricky.",
            "Like to grasp the weakness of others, calculate or control others.",
            "Very stingy, never give up money easily."
        ),

        ),
    ChineseZodiacData(
        title = "Rooster",
        smallImage = R.drawable.small_rooster,
        bigImage = R.drawable.big_rooster,
        imageName = R.drawable.name_rooster,
        relationshipDescription = "Roosters treat love very simply. They don’t have rhetoric or circumspect, but they have guidelines in their hearts. If they like the other, they will never twitch or hesitated, they will accept the other directly. Of course, if they don’t like the other, They will not hesitate to say it and never prevaricate, and they will avoid to see the other. but once they get married, they will love their home wholeheartedly, and will do everything with their heart and soul. Both men and women of the rooster are good hands in life, moreover. They are experts in financial management.",
        advantages = listOf(
            "Strong presentation and expressiveness, and able to pay attention to the details of the work force.",
            "He is gentle, humble and cautious, and has a strong economic concept.",
            "Strong vanity, love to enjoy, likable, trendy, seduction to the opposite sex, often unable to restrain.",
            "Deliberately dress up, speak and be good at things, be able to attract the opposite sex, and have a good eye. Once you find that the dating one not ideal, you will have the courage to cut love with the sword of wisdom.",
            "Good luck in a life-long journey, a wealth of material life, a successful career, although there is fortune, the expenses are also large, so it is difficult to save assets.",
            "Most of the roosters are attractive, beautiful or handsome."
        ),
        disadvantages = listOf(
            "Arrogant and conceited, demanding of others is very strict, should be less nagging, less nosy, avoid unnecessary trouble, then they will be popular.",
            "The appearance seems radical, but in fact, he is a very conservative person.",
            "When in a negative state, you will be very stubborn.",
            "Only admit their strengths, but do not admit their shortcomings.",
            "Love bluffing and can’t understand themselves really.",
            "Self-righteous, can't really know what to show off."
        ),
    ),
    ChineseZodiacData(
        title = "Dog",
        smallImage = R.drawable.small_dog,
        bigImage = R.drawable.big_dog,
        imageName = R.drawable.name_dog,
        relationshipDescription = "Dog people are loyal to their duties, their activity is very eye-catching, enough to arouse goodwill of the opposite sex. They are frank and never pretentious. They like to share worries for others. They know how to be considerate and caring about others. They will definitely be good friend of their partners and children. They will not fall into love like horses and tigers in their emotional life, but they can snuggle their loved ones silently and guard their family and give care to family.",
        advantages = listOf(
            "Be upright, obey the rules, and have a sense of responsibility.",
            "Respect the boss and elders, work hard, and have a strong self-concept.",
            "Lack of flexibility and expressiveness, so often lose many beautiful things and have a strong sense of defense.",
            "Not easy to hide their emotion, both likes and dislikes are obviously seen. Once you give out your true feelings, you will treat the other wholeheartedly, and you are straightforward, innocent, and most of dogs get married early.",
            "Good fortune in life, with help from noble people, high promotion rate after middle age, if you work hard to realize your potential, you could reach some like an excellent supervisor.",
            "Know how to get along with others."
        ),
        disadvantages = listOf(
            "Emotional instability, need some self-restraint, and continuous to absorb new knowledge and ways of dealing with the world.",
            "Sometime might be nosy or gossip.",
            "Stick to your own opinions, be stubborn to the end, and swear to never give up until you reach your goal.",
            "It's a bit mean sometime and it’s hard to change opinions mostly.",
            "Easy to be irritable and easy to get angry.",
            "Weak endurance."
        ),
    ),
    ChineseZodiacData(
        title = "Pig",
        smallImage = R.drawable.small_pig,
        bigImage = R.drawable.big_pig,
        imageName = R.drawable.name_pig,
        relationshipDescription = "Pigs love to be clean and like to arrange their homes in an orderly manner. In terms of feelings, they naturally hope that someone with a similar thoughts can help them and give them a warm home. They don’t like hypocritical people or lazy and pointless people, but they like the people who is motivated, capable, and responsible. They like their significant partner smart and simple, have a fearless spirit, and are upright people.",
        advantages = listOf(
            "Noble justice, human affection, and innocence.",
            "Strict self-discipline, lack of adaptability, developed economic concepts, and passive rationality.",
            "Usually taciturn, arbitrarily acting alone, often upset by things of the opposite sex.",
            "Innocent and cute, cheerful in the chest, and rich in feelings. They are not good on expression of love, but they have the true love is deep in the heart.",
            "In the Chinese zodiac, pig is one wealth sign, with lucky stars are shining, you can get ahead when you are young.",
            "No matter what kind of industry you are engaged in, you are likely to become a man of great power."
        ),
        disadvantages = listOf(
            "Lack of patience in doing things, act according to one's own will, will not shift the goal or use the hand bowl, must act cautiously.",
            "The point of view is unclear and a bit confused.",
            "Not paying attention to other people's feelings.",
            "Too kind and easy to believe other people's lies.",
            "It is not easy to control money, because the heart is too soft to grasp the money bag.",
            "Not so sensitive to the spiritual world."
        ),
    ),
    ChineseZodiacData(
        title = "Rat",
        smallImage = R.drawable.small_rat,
        bigImage = R.drawable.big_rat,
        imageName = R.drawable.name_rat,
        relationshipDescription = "Like to enjoy life with taste, talented and smart, surrounded by people, they are often recognized to be in constant love and lack of loyalty, they just want to pursue a lifelong partner and too busy in the pursuit, women attentive and strong tendency, good at communication and doing things straight forward, most women could be a good housewife like house clean and well organized, most men active outside and play the role fully as a lead.",
        advantages = listOf(
            "Pay attention to relationship; have great ambitions; Very good on managing financial life, smart, energetic, meticulous, social and humorous.",
            "Tact with many ideals and consideration with empathy.",
            "Popular and attractive",
            "Versatile.",
            "Women love to be clean and tidy up housework in an older manner.",
            "Optimistic, smart to learn everything and can handle it skillfully.",
            "Active and swift with profitable things,",
            "Fantastic and likable, good at taking advantage of opportunities.",
            "Keen and omnipotent, good at multi-faceted management.",
            "Strong curiosity, wants to get in touch with everything quickly."
        ),
        disadvantages = listOf(
            "No strong courage to do things.",
            "Some stubborn, could act filthy when lucrative work.",
            "Life habit of late sleeper, not good for healthy.",
            "Good in nature but sometimes could be impolite.",
            "Some selfish individualistic",
            "Could be picky with short minded."
        ),
    ),
    ChineseZodiacData(
        title = "Ox",
        smallImage = R.drawable.small_ox,
        bigImage = R.drawable.big_ox,
        imageName = R.drawable.name_ox,
        relationshipDescription = "For them, falling in love is just a warm-up process for their marriage. They are not good at rhetoric, and especially hate duplicity. The concept of love focuses on the practical sense of life, giving others the impression of lack of interest, very unromantic, and lonely on the surface, lack of romance even the heart is filled with rich emotions. It would show up until they meet the people who agree with them. Strong autonomy and independence, who will often develop unknowingly potential in difficult situations. Generally, they like to be listed as marriage partners when they are dating; women are good housekeepers, are good at educating their children, and take great importance on the family. Men are not easy to grasp the psychology of girls because they can’t speak softly and sweetly. In short, men and women born in the year of the ox, if they like the person, they must put aside the conservative attitude and behave more open and positively, making the speech and actions clear and quick. In this way, other people have the opportunity to understand your inner beauty and reach a beautiful destination.",
        advantages = listOf(
            "Be cautious, down-to-earth, slow action, strong physique, resilient, and self-sacrifice.",
            "Do things in accordance with one's own ideas and abilities.",
            "Before taking any action, I have long thought about it, and I have a strong belief and strong physical strength from beginning to end.",
            "Have a strong temper with a strong entrepreneurial spirit, follow the steps with the most endurance and have a good sense of right or wrong.",
            "There is a strong desire for self-expression in the heart and born with excellent leadership., so it is not suitable for backstage.",
            "Women are well-managed and traditionally good wife at home, and they take the great importance to the education of their children.",
            "Although the marriage is not harmony, OX people can devote their energy to the career to become an accomplished entrepreneur.",
            "Be patient and willing to make progress, so you can achieve the goals you have set.",
            "Kindness and honesty are the nature of lifelong, value work and family with ideals and ambitions, have a strong love for the country.",

            ),
        disadvantages = listOf(
            "Women are less feminine. If you can recognize your own shortcomings, change your rigid and cold attitude and show yourself emotionally.",
            "Due to hard work and stubborn personality, not listening to advice, often forget to eat, and have gastrointestinal problems.",
            "Don't know how to be flexible and bend themselves, make others lost interests.",
            "Silent eloquence and poor social interaction.",
            "Reticence and resisting on their own idea, don't believe in others' ideas.",
            "Always like to do their own way, stubborn and not flexible.",
        ),
    ),
    ChineseZodiacData(
        title = "Tiger",
        smallImage = R.drawable.small_tiger,
        bigImage = R.drawable.big_tiger,
        imageName = R.drawable.name_tiger,
        relationshipDescription = "Like to be immersed in love, men pay more attention to actual behavior, do not speak sweet words, and do not create a romantic atmosphere, and exude a unique and attractive charm in behavior, which will attract women’s attention and maternal love, so the partners of tiger man must has a endless tolerant and unrighteous mother love. The tiger woman will exude a reminiscent demeanor, without hesitation and procrastination, they emphasize loyalty, full of self-confident. If you can get along as a good friend, you can get along for a long time. The relationship should start from appreciating their talents, the men could have problem if greedy for their beauty and have sex easily.",
        advantages = listOf(
            "The personality is more stubborn, hard-line, and arbitrarily alone, adventurous and aggressive, more frustrated, more ambitious. They are full of confidence in themselves.",
            "The rich man is enthusiastic and brave, and his adventurous spirit is extraordinary.",
            "Express themselves boldly and aggressively when doing things.",
            "Keep everything they say and do what they say, and never go back.",
            "Love activities and sports, show the limelight, have chivalrous heart, and be frank and upright and easy to win trust.",
            "The appearance of Tiger is mighty even they are not angry, they are confident to be a leader. The character is firm and tenacious and never bows his head. He will never rest until everything is done, and he can be qualified for any leadership position.",
            "They don’t think for anything bad, they are not used to store things up for emergencies in the future.",
            "Tiger people are born to like to accept challenges, do not like to obey people, and need the people to obey them.",
        ),
        disadvantages = listOf(
            "Quite rebellious, often overconfident and unable to coordinate with others, like to be alone, and often be extremely ruthless.",
            "Lack of romantic sentiment, play the dictatorship to his wife, and lack a happy family life.",
            "Although they have a wide range of acquaintances, they are hard to make close friends, they are stubborn, do whatever they want to achieve their goals, are domineering, like agitation, and have a strong sense of self.",
            "In terms of investment, some like those who can recover in a short period of time. It is not interesting if it is too long.",
            "In rural livestock production, the tiger is not allowed to watch, because their magnetic field will make the mother feel uneasy and unsafe so will eat the young child to show defense.",

            ),
    ),
    ChineseZodiacData(
        title = "Rabbit",
        smallImage = R.drawable.small_rabbit,
        bigImage = R.drawable.big_rabbit,
        imageName = R.drawable.name_rabbit,
        relationshipDescription = "The way of falling in love gives people the feeling that they are wearing a hazy veil. They are good at rational thinking and like to date in romantic and noble places. The personality is gentle and romantic, but sometimes they are indecision. You must show tolerance when interacting with them, and encourage them to express their emotions and opinions. Men sometime are critical, only compatible with informal women who are not narrow-minded. After marriage, rabbit women prefer to take care of family than working outside under a good financial status.",
        advantages = listOf(
            "Mindful, gentle and considerate personality.",
            "With language genius and sharp eloquence, it is quite popular.",
            "The personality is fickle, quite conservative and calm-headed.",
            "Like a smooth and waveless love life.",
            "Sociable and friendly, talk and laugh lively, personable and cautious. Dislike arguing with others, with a gentle temperament that can turn enemies into friends.",
            "Rabbits of type O blood are bold, and become brave and decisive as soon as their style is revealed.",
            "Both men and women advocate family first.",
            "The rabbit character is very particular about beauty, they like home furnishings on some particular style ,they are polite and thoughtful, sympathetic, and helpful.",
            "The rabbits seem to be kind-hearted and compassionate, and don't always insist on their own opinions. They will never get angry easily, because they are kind-hearted.",
        ),
        disadvantages = listOf(
            "There is a tendency of fraternity in the public, and the mentality of being a public lover, which is prone to emotional disputes.",
            "Lack of thinking and determination but often failing of passion.",
            "On the surface, you are a good gentleman, and following the general rule, but they are quite stubborn on the bottom of heart.",
            "Most of them are unwilling to live too tediously and will continue to create interests in life, but they often will delve into the thing that are not good at it.",
            "Being too cautious in everything, unwilling to confide in others or open their hearts, has a tendency to escape reality, sometimes they are too conservative to lose opportunities. ",
            "Women are sensitive and sentimental, gentle and delicate.",
        ),
    ),
)
val chineseZodiacSmallImageList = chineseZodiacMainList.map { it.smallImage }*/
