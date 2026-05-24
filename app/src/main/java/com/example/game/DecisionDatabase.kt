package com.example.game

object DecisionDatabase {

    val earlyLifeEvents = listOf(
        Decision(
            id = "early_starter_carrier",
            title = "A Fork in the Road",
            scenario = "You are 18, standing at life's doorstep with 1,200 Rs/USD in your pocket. How will you build your future?",
            stage = DecisionStage.EARLY_LIFE,
            minAge = 18,
            maxAge = 19,
            options = listOf(
                ChoiceOption(
                    text = "Enroll in local Tech Boot Camp",
                    description = "-400 cash, +30 professional skills. Modern trade learning.",
                    impact = ConsequenceImpact(
                        moneyChange = -400.0,
                        skillsImpact = SkillStats(academic = 10, professional = 30, social = 10),
                        educationSet = "Tech bootcamp graduate",
                        narrative = "Enrolled in a fast-paced coding bootcamp. You spent nights slugging energy drinks, writing JavaScript."
                    )
                ),
                ChoiceOption(
                    text = "Pursue traditional University Degree",
                    description = "-800 cash, +35 academic skills. Classic path to prestige.",
                    impact = ConsequenceImpact(
                        moneyChange = -800.0,
                        skillsImpact = SkillStats(academic = 35, professional = 10, social = 15),
                        educationSet = "College freshman",
                        narrative = "Enrolled in standard business administration. The lecture halls are dusty, but of course social networking is top tier."
                    )
                ),
                ChoiceOption(
                    text = "Skip study & Freelance directly",
                    description = "+250 cash, +25 creative skills. Autonomy and risk.",
                    impact = ConsequenceImpact(
                        moneyChange = 250.0,
                        skillsImpact = SkillStats(creative = 25, professional = 15, social = 5),
                        jobSet = "Rookie Freelancer",
                        narrative = "You skipped study entirely. You set up a cheap desk, registered on Fiverr, and began cold-pitching simple logo designs."
                    )
                ),
                ChoiceOption(
                    text = "Take a Retail sales job",
                    description = "+600 cash, +20 social skills. Immediate income flow.",
                    impact = ConsequenceImpact(
                        moneyChange = 600.0,
                        skillsImpact = SkillStats(social = 20, professional = 10),
                        jobSet = "Retail Assistant",
                        narrative = "Took a front-desk job at an electronics store. Talking to annoyed customers all day boosted your conversation skills!"
                    )
                )
            )
        ),
        Decision(
            id = "early_side_hustle",
            title = "The Midnight Oil",
            scenario = "Your monthly living costs are eating your cash fast. You need extra money this weekend. What do you choose?",
            stage = DecisionStage.EARLY_LIFE,
            minAge = 19,
            maxAge = 22,
            options = listOf(
                ChoiceOption(
                    text = "Write blogs using AI filters",
                    description = "+150 cash, +10 creative. Quick and easy.",
                    impact = ConsequenceImpact(
                        moneyChange = 150.0,
                        skillsImpact = SkillStats(creative = 10, professional = 5),
                        happinessChange = -5,
                        narrative = "You churned out SEO articles on tech gadgets. Easy cash, but your brain felt completely numb."
                    )
                ),
                ChoiceOption(
                    text = "Build small websites for local shops",
                    description = "+400 cash, +15 professional skills, requires 20 Professional.",
                    impact = ConsequenceImpact(
                        moneyChange = 400.0,
                        skillsImpact = SkillStats(professional = 15, social = 10),
                        reputationChange = 5,
                        narrative = "You walked from bakery to bakery. Built a neat responsive booking site for a local coffee shop. They loved it!"
                    ),
                    requiredStats = StatsPredicate(minProfessional = 20)
                ),
                ChoiceOption(
                    text = "Work extra shifts at a local diner",
                    description = "+200 cash, -10 health. Physically demanding.",
                    impact = ConsequenceImpact(
                        moneyChange = 200.0,
                        healthChange = -10,
                        happinessChange = -5,
                        narrative = "You washed pots and carried bags till 2:00 AM. Your back is killing you, but it's cold hard currency in hand."
                    )
                )
            )
        ),
        Decision(
            id = "early_first_romance",
            title = "A Warm Glance",
            scenario = "While sitting at a local bakery, you meet Samantha/Sameer, an ambitious designer. They invite you out. Do you go?",
            stage = DecisionStage.EARLY_LIFE,
            minAge = 18,
            maxAge = 24,
            options = listOf(
                ChoiceOption(
                    text = "Go on an expensive high-end date",
                    description = "-200 cash, +25 happiness. Sparks fly instantly!",
                    impact = ConsequenceImpact(
                        moneyChange = -200.0,
                        happinessChange = 25,
                        reputationChange = 5,
                        partnerSet = "Sam (The Creative Soul)",
                        narrative = "You hit a high-rise candle-lit bistro. You discussed photography, travel, and goals. They are officially your partner now!"
                    )
                ),
                ChoiceOption(
                    text = "Suggest an affordable street walk",
                    description = "-30 cash, +15 happiness. Humble and engaging.",
                    impact = ConsequenceImpact(
                        moneyChange = -30.0,
                        happinessChange = 15,
                        partnerSet = "Sam (The Down-to-Earth)",
                        narrative = "You bought simple ice-creams and walked around the public park for three hours. The conversation flowed naturally. They are now your partner!"
                    )
                ),
                ChoiceOption(
                    text = "Reject them and stay studying",
                    description = "+15 academic, +10 professional. Fully focused.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(academic = 15, professional = 10),
                        happinessChange = -5,
                        narrative = "You made a polite excuse and returned to studying machine learning algorithms. Romance can wait; success cannot."
                    )
                )
            )
        ),
        Decision(
            id = "early_crypto_bubble",
            title = "FOMO Frenzy",
            scenario = "A new meme cryptocurrency called 'HustleElonCoin' is soaring 500% in online forums. Everyone is buying. What do you do?",
            stage = DecisionStage.EARLY_LIFE,
            minAge = 19,
            maxAge = 25,
            options = listOf(
                ChoiceOption(
                    text = "Speculate with your savings (300 Units)",
                    description = "50% chance to double and exit vs 50% chance to lose it all.",
                    impact = ConsequenceImpact(
                        moneyChange = -300.0, // base cost, ViewModel randomizes the reward
                        narrative = "You FOMO'd and put 300 savings in HustleElonCoin. (The economy system will resolve your luck later!)."
                    )
                ),
                ChoiceOption(
                    text = "Avoid cryptocurrency completely",
                    description = "+10 academic skills. Smart financial restraint.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(academic = 10),
                        happinessChange = 5,
                        narrative = "You chose sanity. You read a classic book on value investing instead of reading dogecoin forums. Solid discipline!"
                    )
                )
            )
        ),
        Decision(
            id = "early_startup_gamble",
            title = "The Garage Pitch",
            scenario = "Your flatmate wants to form a indie game studio. He wants you to contribute 600 cash and lead programming.",
            stage = DecisionStage.EARLY_LIFE,
            minAge = 20,
            maxAge = 24,
            options = listOf(
                ChoiceOption(
                    text = "Yes, let's take the risk!",
                    description = "-600 cash, +40 creative skills. High passion project.",
                    impact = ConsequenceImpact(
                        moneyChange = -600.0,
                        skillsImpact = SkillStats(creative = 40, professional = 20),
                        jobSet = "Indie Game Developer",
                        narrative = "You teamed up! Spent months writing shaders, modeling pixels. The game launch went viral on Steam!"
                    )
                ),
                ChoiceOption(
                    text = "Only volunteer part-time",
                    description = "-200 cash, +15 creative, +10 professional.",
                    impact = ConsequenceImpact(
                        moneyChange = -200.0,
                        skillsImpact = SkillStats(creative = 15, professional = 10),
                        narrative = "You did code contributions over weekends. The game had mild local sales, yielding a small side-hustle credit."
                    )
                ),
                ChoiceOption(
                    text = "Decline and stick to safe plans",
                    description = "+15 professional skills. Zero risk.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(professional = 15),
                        narrative = "You said 'No thanks'. You stayed working your stable hours. Safe, steady, and predictable."
                    )
                )
            )
        )
    )

    val midLifeEvents = listOf(
        Decision(
            id = "mid_career_burnout",
            title = "The 80-Hour Wall",
            scenario = "You have been putting in crazy nights at work. Your face looks pale, and you can barely sleep. Burnout is imminent.",
            stage = DecisionStage.MID_LIFE,
            minAge = 26,
            maxAge = 40,
            options = listOf(
                ChoiceOption(
                    text = "Push through to secure the promotion",
                    description = "+3,000 money, -30 health, +15 reputation. Career warrior.",
                    impact = ConsequenceImpact(
                        moneyChange = 3000.0,
                        healthChange = -30,
                        reputationChange = 15,
                        happinessChange = -15,
                        narrative = "You lived in the cubicle. You got the promotion and a big fat check, but you collapsed on your bathroom floor twice last week."
                    )
                ),
                ChoiceOption(
                    text = "Take a mental health leave (Sabbatical)",
                    description = "-300 money, +25 health, +20 happiness.",
                    impact = ConsequenceImpact(
                        moneyChange = -300.0,
                        healthChange = 25,
                        happinessChange = 20,
                        narrative = "You packed and spent 3 weeks hiking in nature. Your vision cleared and your heartbeat normalized. Your boss was mildly annoyed but complied."
                    )
                ),
                ChoiceOption(
                    text = "Quiet-Quit: Do the bare minimum",
                    description = "+500 money, -10 reputation. Peaceful scaling.",
                    impact = ConsequenceImpact(
                        moneyChange = 500.0,
                        reputationChange = -10,
                        happinessChange = 10,
                        narrative = "You turned your company laptop off at exactly 5:00 PM. Performance review was average, but you gained valuable personal hours back."
                    )
                )
            )
        ),
        Decision(
            id = "mid_housing_advise",
            title = "A Place to Call Home",
            scenario = "Your landlord raised your room rent. You are thinking about buying your own starter apartment or finding a better rental.",
            stage = DecisionStage.MID_LIFE,
            minAge = 27,
            maxAge = 45,
            options = listOf(
                ChoiceOption(
                    text = "Sign a rental lease for a luxury flat",
                    description = "-400 cash, +15 health, +15 happiness. Solid comfort.",
                    impact = ConsequenceImpact(
                        moneyChange = -400.0,
                        healthChange = 15,
                        happinessChange = 15,
                        houseSet = "Luxury Smart Flat (Rented)",
                        narrative = "Moved into a shiny smart condo with a gym and pool. Cooking here felt like being a gourmet chef! Life quality upgraded!"
                    )
                ),
                ChoiceOption(
                    text = "Buy a cozy suburban apartment",
                    description = "-2,500 downpayment, +30 reputation, +20 happiness. Long-term asset.",
                    impact = ConsequenceImpact(
                        moneyChange = -2500.0,
                        reputationChange = 30,
                        happinessChange = 20,
                        houseSet = "Suburban Condo (Owned)",
                        narrative = "Signed the bank papers! You now own a cozy apartment with a small balcony. Fixing your own doors feels incredibly satisfying!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 2500.0)
                ),
                ChoiceOption(
                    text = "Stay in parent's house or budget hostel",
                    description = "+400 cash, -10 happiness, -5 health. Saving aggressively.",
                    impact = ConsequenceImpact(
                        moneyChange = 400.0,
                        happinessChange = -10,
                        healthChange = -5,
                        narrative = "You stayed in the cramped damp room to save every single coin. The sink leaks and noise levels are high, but your savings rate is sky-high."
                    )
                )
            )
        ),
        Decision(
            id = "mid_marriage_proposal",
            title = "Lifetime Alliance",
            scenario = "Your partner suggests it's time to marry and buy a house together. They want to tie the knot this summer.",
            stage = DecisionStage.MID_LIFE,
            minAge = 25,
            maxAge = 45,
            options = listOf(
                ChoiceOption(
                    text = "Throw a grand luxury wedding!",
                    description = "-1,500 cash, +30 reputation, +35 happiness.",
                    impact = ConsequenceImpact(
                        moneyChange = -1500.0,
                        reputationChange = 30,
                        happinessChange = 35,
                        partnerSet = "Spouse (Happy & Loving)",
                        narrative = "A massive celebration! 400 guests, top-shelf catering, amazing lights. You danced all night. Your social status rocketed!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 1500.0, requiresPartner = true)
                ),
                ChoiceOption(
                    text = "Organize a simple registry court marriage",
                    description = "-200 cash, +25 happiness. Focused on intimacy.",
                    impact = ConsequenceImpact(
                        moneyChange = -200.0,
                        happinessChange = 25,
                        partnerSet = "Spouse (Down-to-Earth)",
                        narrative = "A private signing at the registry office followed by family lunch. It was simple, deeply emotional, and budget-friendly."
                    ),
                    requiredStats = StatsPredicate(requiresPartner = true)
                ),
                ChoiceOption(
                    text = "Refuse and separate (Solo Focus)",
                    description = "+10 professional skills, -25 happiness. Relationship ends.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(professional = 10),
                        happinessChange = -25,
                        isPartnerRemove = true,
                        narrative = "You told them you weren't ready for commitment. It ended in a tearful break-up. You are single again, cold and alone."
                    ),
                    requiredStats = StatsPredicate(requiresPartner = true)
                )
            )
        ),
        Decision(
            id = "mid_stock_crash",
            title = "A Sea of Red",
            scenario = "A surprise Federal hike causes the global stock market to crash 30% in three days. Panic is everywhere on CNBC.",
            stage = DecisionStage.MID_LIFE,
            minAge = 28,
            maxAge = 48,
            options = listOf(
                ChoiceOption(
                    text = "Panic Sell! Liquidate everything to cash",
                    description = "-300 cash, -10 happiness. Save what is left.",
                    impact = ConsequenceImpact(
                        moneyChange = -300.0,
                        happinessChange = -10,
                        narrative = "You panicked, sold your portfolio at the absolute bottom. Safe now, but locked in massive losses."
                    )
                ),
                ChoiceOption(
                    text = "Buy the fear! Invest aggressively (800 cash)",
                    description = "Requires 800 cash. Growth opportunity in 2 years.",
                    impact = ConsequenceImpact(
                        moneyChange = -800.0,
                        skillsImpact = SkillStats(academic = 15),
                        narrative = "You kept your head. You poured 800 units of dry powder into reliable index blue chips at massive discounts!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 800.0)
                ),
                ChoiceOption(
                    text = "Close the stock app & go gym",
                    description = "+10 health, +5 happiness. Zen focus.",
                    impact = ConsequenceImpact(
                        healthChange = 10,
                        happinessChange = 5,
                        narrative = "You closed the monitor and ran 5 kilometers outside. Let the market fix itself while you stay healthy."
                    )
                )
            )
        ),
        Decision(
            id = "mid_ethics_hostage",
            title = "The Corporate Secret",
            scenario = "Your enterprise director asks you to quietly inject competitor scraping bots into the software code. It is highly illegal.",
            stage = DecisionStage.MID_LIFE,
            minAge = 26,
            maxAge = 50,
            options = listOf(
                ChoiceOption(
                    text = "Do it and accept the silent bonus",
                    description = "+1,800 cash, -20 reputation. Risk of investigation.",
                    impact = ConsequenceImpact(
                        moneyChange = 1800.0,
                        reputationChange = -20,
                        happinessChange = -10,
                        narrative = "You pushed the code quietly. The company made millions; you received an envelope with thick bonus cash but had nightmares."
                    )
                ),
                ChoiceOption(
                    text = "Report the request to compliance",
                    description = "+25 reputation, -10 happiness. Honest agent.",
                    impact = ConsequenceImpact(
                        reputationChange = 25,
                        happinessChange = -10,
                        narrative = "You blew the whistle! The compliance board suspended your director. You became a workspace icon of honesty, but corporate friction got cold."
                    )
                ),
                ChoiceOption(
                    text = "Politely decline and change jobs",
                    description = "+15 professional skills, +5 health. Healthy transition.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(professional = 15),
                        healthChange = 5,
                        jobSet = "Hustling Consultant",
                        narrative = "You said 'This is against my coding practices'. You resigned the next week and set up your own independent consultancy office."
                    )
                )
            )
        )
    )

    val lateLifeEvents = listOf(
        Decision(
            id = "late_saas_exit",
            title = "A Multi-Million Deal",
            scenario = "Your side product SaaS tool is attracting huge attention. A tech conglomerate offers to buy it for 8,500 cash outright.",
            stage = DecisionStage.LATE_LIFE,
            minAge = 46,
            maxAge = 65,
            options = listOf(
                ChoiceOption(
                    text = "Sell the asset! (Acquisition Holiday)",
                    description = "+8,500 cash, +40 happiness, +30 reputation. Exit success!",
                    impact = ConsequenceImpact(
                        moneyChange = 8500.0,
                        happinessChange = 40,
                        reputationChange = 30,
                        jobSet = "Wealthy Jetsetter",
                        narrative = "Acquisition completed! Your phone received wire alerts. Tech crunch wrote an article on your success story!"
                    )
                ),
                ChoiceOption(
                    text = "Reject offer and scale to IPO",
                    description = "-1,000 cash, +50 professional skills. Hard road.",
                    impact = ConsequenceImpact(
                        moneyChange = -1000.0,
                        skillsImpact = SkillStats(professional = 50),
                        reputationChange = 15,
                        narrative = "You refused. You doubled down on hiring engineers and took a bank loan. Extreme pressure, extreme ambition."
                    ),
                    requiredStats = StatsPredicate(minMoney = 1000.0)
                )
            )
        ),
        Decision(
            id = "late_spiritual_call",
            title = "The Void of Wealth",
            scenario = "You look at your Bank Statement. Some metrics look robust, but you feel an empty space inside. How do you seek peace?",
            stage = DecisionStage.LATE_LIFE,
            minAge = 50,
            maxAge = 75,
            options = listOf(
                ChoiceOption(
                    text = "Donate 1,500 to fund rural clinics",
                    description = "-1,500 cash, +40 reputation, +35 happiness.",
                    impact = ConsequenceImpact(
                        moneyChange = -1500.0,
                        reputationChange = 40,
                        happinessChange = 35,
                        narrative = "You funded health complexes. Hearing reports of saved tribal children filled your spirit with a bliss money can never buy."
                    ),
                    requiredStats = StatsPredicate(minMoney = 1500.0)
                ),
                ChoiceOption(
                    text = "Go on a luxurious Alpine retreat",
                    description = "-600 cash, +25 health, +20 happiness.",
                    impact = ConsequenceImpact(
                        moneyChange = -600.0,
                        healthChange = 25,
                        happinessChange = 20,
                        narrative = "You spent two weeks in Swiss sanatoriums: thermal muds, mineral massage. Your joints feel brand new!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 600.0)
                ),
                ChoiceOption(
                    text = "Read ancient philosophy papers",
                    description = "+20 academic skills, +10 happiness. Cost-free zen.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(academic = 20),
                        happinessChange = 10,
                        narrative = "You stayed home reading Marcus Aurelius and Seneca. You understood that control of emotions exceeds control of gold."
                    )
                )
            )
        ),
        Decision(
            id = "late_health_scare",
            title = "The Faltering Engine",
            scenario = "During an annual check, doctors notice a critical blockage in your cardiovascular system. Immediate surgical procedures are urged.",
            stage = DecisionStage.LATE_LIFE,
            minAge = 55,
            maxAge = 75,
            options = listOf(
                ChoiceOption(
                    text = "Book the elite private cardiac clinic",
                    description = "-3,000 money, +50 health. Superb survival chance.",
                    impact = ConsequenceImpact(
                        moneyChange = -3000.0,
                        healthChange = 50,
                        happinessChange = 10,
                        narrative = "The top-tier surgeons handled your case. Robotic surgery was fully successful. You woke up feeling clean and light!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 3000.0)
                ),
                ChoiceOption(
                    text = "Get standard government healthcare",
                    description = "-800 money, +25 health. Waiting queue risk.",
                    impact = ConsequenceImpact(
                        moneyChange = -800.0,
                        healthChange = 25,
                        happinessChange = -10,
                        narrative = "Waited in public queue lines. The operation was standard, stressful, but resolved the immediate lethal threat."
                    ),
                    requiredStats = StatsPredicate(minMoney = 800.0)
                ),
                ChoiceOption(
                    text = "Ignore medical advice & consume herbs",
                    description = "+100 money, -35 health. Extreme danger.",
                    impact = ConsequenceImpact(
                        moneyChange = 100.0,
                        healthChange = -35,
                        happinessChange = -15,
                        narrative = "You self-treated with herbal juices. Modern medicine is a sham, you claimed. Your breathing remains shallow and heavy."
                    )
                )
            )
        ),
        Decision(
            id = "late_heir_seed",
            title = "The Next Generation",
            scenario = "Your child wishes to open a sustainable smart-energy franchise. They present a thorough 15-page plan and ask for a 2,000 cash seed asset.",
            stage = DecisionStage.LATE_LIFE,
            minAge = 50,
            maxAge = 72,
            options = listOf(
                ChoiceOption(
                    text = "Fully sponsor their company! (2,000 cash)",
                    description = "-2,000 cash, +30 happiness, +15 reputation. Proud parent.",
                    impact = ConsequenceImpact(
                        moneyChange = -2000.0,
                        happinessChange = 30,
                        reputationChange = 15,
                        narrative = "You transferred the funds with a warm hug. Their smart franchise was a huge neighborhood hit! They look up to you with intense gratitude."
                    ),
                    requiredStats = StatsPredicate(minMoney = 2000.0)
                ),
                ChoiceOption(
                    text = "Give them a small advisor tip (300 cash)",
                    description = "-300 cash, +10 happiness, +10 academic skills.",
                    impact = ConsequenceImpact(
                        moneyChange = -300.0,
                        happinessChange = 10,
                        skillsImpact = SkillStats(academic = 10),
                        narrative = "You offered 300 units and sat with them for a weekend reviewing their business calculations. They learned the worth of bootstrapping!"
                    ),
                    requiredStats = StatsPredicate(minMoney = 300.0)
                ),
                ChoiceOption(
                    text = "Refuse and tell them to work from zero",
                    description = "-10 happiness, +15 professional skills. Tough love.",
                    impact = ConsequenceImpact(
                        happinessChange = -10,
                        skillsImpact = SkillStats(professional = 15),
                        narrative = "No free handouts, you told them. They were deeply hurt, but they went and secured a commercial bank loan. They became independent!"
                    )
                )
            )
        )
    )

    val randomEventsList = listOf(
        Decision(
            id = "random_lottery_win",
            title = "A Golden Ticket",
            scenario = "While checking out a grocery shop, you decided to buy a scratch card for 10 cash on a whim. Under the foil lies a local prize!",
            stage = DecisionStage.RANDOM,
            options = listOf(
                ChoiceOption(
                    text = "Collect your cash prize!",
                    description = "+1,500 cash, +20 happiness. Absolute luck!",
                    impact = ConsequenceImpact(
                        moneyChange = 1500.0,
                        happinessChange = 20,
                        narrative = "Bingo! The card ticket printed the jackpot code. You danced in the grocery store aisle carrying 1,500 units!"
                    )
                )
            )
        ),
        Decision(
            id = "random_wallet_snatch",
            title = "The Back-Alley Confrontation",
            scenario = "A shadows-clad snatcher blocks your home path in a dark alleyway, showing a sharp knife. He demands your wallet.",
            stage = DecisionStage.RANDOM,
            options = listOf(
                ChoiceOption(
                    text = "Hand over your wallet calmly",
                    description = "-250 cash, -10 happiness, +10 health. Safe choice.",
                    impact = ConsequenceImpact(
                        moneyChange = -250.0,
                        happinessChange = -10,
                        healthChange = 10,
                        narrative = "You tossed the cash pouch and stepped back with raised hands. You lost minor funds, but walked home with zero holes in your shirt."
                    )
                ),
                ChoiceOption(
                    text = "Fight him using martial arts moves!",
                    description = "+25 physical skills, 50% chance to beat him vs 50% to suffer deep injuries.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(social = 5, creative = 5), // base
                        narrative = "You yelled and attempted a spinning roundhouse! (The engine resolved your high combat odds...)"
                    )
                )
            )
        ),
        Decision(
            id = "random_viral_post",
            title = "Going Viral",
            scenario = "You posted an incredibly funny, sarcastic software industry video on social media. By nightfall, your phone notifications are glowing hot!",
            stage = DecisionStage.RANDOM,
            options = listOf(
                ChoiceOption(
                    text = "Capitalize on it: Start content creation!",
                    description = "+400 pocket cash, +30 reputation, +25 creative skills.",
                    impact = ConsequenceImpact(
                        moneyChange = 400.0,
                        reputationChange = 30,
                        skillsImpact = SkillStats(creative = 30, social = 10),
                        narrative = "You accepted brand sponsors for coding gear. Fans are posting memes of you! You are officially an online personality!"
                    )
                ),
                ChoiceOption(
                    text = "Delete the post to preserve privacy",
                    description = "+15 academic skills, +10 health. Keep head down.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(academic = 15),
                        healthChange = 10,
                        narrative = "You deleted the accounts immediately. You hate public fame. You slept like an absolute baby in complete anonymity."
                    )
                )
            )
        ),
        Decision(
            id = "random_car_accident",
            title = "The Fender Bender",
            scenario = "A reckless delivery vehicle slams into your taxi / sedan at a road light. The driver claims it is your fault and screams.",
            stage = DecisionStage.RANDOM,
            options = listOf(
                ChoiceOption(
                    text = "Pay them 300 to avoid conflicts",
                    description = "-300 cash, -15 happiness. Quick resolution.",
                    impact = ConsequenceImpact(
                        moneyChange = -300.0,
                        happinessChange = -15,
                        narrative = "You yielded 300 units to stop the yelling. They drove away instantly. You felt frustrated, but got home in peace."
                    )
                ),
                ChoiceOption(
                    text = "Call Traffic Police to handle investigation",
                    description = "+15 social skill, +10 reputation. Honest procedure.",
                    impact = ConsequenceImpact(
                        skillsImpact = SkillStats(social = 15),
                        reputationChange = 10,
                        narrative = "You dialed the police, took photos of the rear bump. The highway patrol arrived, checked cameras, and fined the delivery driver! Sweet justice!"
                    )
                )
            )
        )
    )

    fun getEventById(id: String): Decision? {
        val all = earlyLifeEvents + midLifeEvents + lateLifeEvents + randomEventsList
        return all.find { it.id == id }
    }
}
