-- User Roles
INSERT INTO user_roles (id, user_role)
VALUES (1, 'ADMIN'),
       (2, 'USER');

ALTER TABLE user_roles
    ALTER COLUMN id RESTART WITH 3;

-- Users
INSERT INTO users (id, email, first_name, last_name, image_url, is_active, password)
VALUES (1, 'admin@example.com', 'Admin', 'Adminov', null, 1,
        '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2'),
       (2, 'user@example.com', 'User', 'Userov', null, 1,
        '57e7759fd2d59275fc3c3cd5dd2ace5013b39ee972999412f3f5f5c3382b6765c2571ef86648abe2');

ALTER TABLE users
    ALTER COLUMN id RESTART WITH 3;

-- User Role Mapping
INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

-- Brands
INSERT INTO brands (id, name)
VALUES (1, 'BMW'),
       (2, 'Audi'),
       (3, 'Mercedes-Benz');

ALTER TABLE brands
    ALTER COLUMN id RESTART WITH 4;

INSERT INTO models (id, name, category, start_year, end_year, brand_id, image_url)
VALUES
-- BMW Models
(1, '3 Series', 'CAR', 1975, null, 1, 'https://assets-clean.local-car-finder.com/images/15235/15235_st1280_089.png'),
(2, 'X5', 'CAR', 1999, null, 1, 'https://autobild.bg/wp-content/uploads/2019/09/BMW-X5-xDrive45e-iPerformance-0_highRes_the-new-bmw-x5-xdriv.jpg'),
(3, 'Z4', 'CAR', 2002, null, 1, 'https://cdn.wheel-size.com/automobile/body/bmw-z4-2022-2025-1720500064.5161343.jpg'),

-- Audi Models
(4, 'A4', 'CAR', 1994, null, 2, 'https://cdn.wheel-size.com/automobile/body/audi-a4-2015-2020-1607000607.1045964.jpg'),
(5, 'Q7', 'CAR', 2005, null, 2, 'https://cdn.wheel-size.com/automobile/body/audi-q7-2019-2024-1716201187.9541745.jpg'),
(6, 'TT', 'CAR', 1998, 2023, 2, 'https://cdn.wheel-size.com/automobile/body/audi-tt-2019-2023-1716276934.0107296.jpg'),

-- Mercedes Models
(7, 'C-Class', 'CAR', 1993, null, 3, 'https://www.mbusa.com/content/dam/mb-nafta/us/myco/my25/c-class/class-page/series/2025-C-SEDAN-CT-1-6-01-DR.jpg'),
(8, 'GLE', 'CAR', 2015, null, 3, 'https://cdn.wheel-size.com/automobile/body/mercedes-gle-class-coupe-amg-2023-2024-1679979349.3835258.jpg'),
(9, 'CLA', 'CAR', 2013, null, 3, 'https://rk.mb-qr.com/media/thumbnails/cards/Front_zeWoV6s.png.1000x514_q95_width-1000_XwC1rin.png.1000x1000_q95.png');

ALTER TABLE models
    ALTER COLUMN id RESTART WITH 10;

-- Offers
INSERT INTO offers (id, description, engine, image_url, mileage, price, transmission, manufacture_year, model_id,
                    seller_id, created)
VALUES
-- BMW Offers
(1, 'Sleek BMW 3 Series', 'GASOLINE', 'https://www.edmunds.com/assets/m/for-sale/a5-3mw89cw07s8f66230/img-1-600x400.jpg', 120000, 22000, 'MANUAL', 2018, 1, 1, '2024-06-10T10:30:00'),
(2, 'Spacious BMW X5', 'DIESEL', 'https://larte-design.com/storage/app/media/kits/bmw/x5/colors/manhattan/bmw-x5-g05-body-kit-carbon-front-manhattan.webp', 85000, 35000, 'AUTOMATIC', 2020, 2, 2, '2024-06-09T10:30:00'),
(3, 'Sporty BMW Z4', 'GASOLINE', 'https://cdn.dealeraccelerate.com/cam/34/6467/440876/1920x1440/2004-bmw-z4-3-0i-roadster', 60000, 45000, 'MANUAL', 2021, 3, 1, '2024-06-08T10:30:00'),
(4, 'BMW 3 Series in mint condition', 'GASOLINE', 'https://www.edmunds.com/assets/m/for-sale/a5-3mw89cw07s8f66230/img-1-600x400.jpg', 30000, 27000, 'AUTOMATIC', 2022, 1, 2, '2024-06-01T11:00:00'),

-- Audi Offers
(5, 'Reliable Audi A4', 'DIESEL', 'https://cars.usnews.com/static/images/Auto/izmo/Colors/audi_14a4sedan4a_volcanoredmetallic.jpg', 100000, 21000, 'MANUAL', 2017, 4, 2, '2024-06-07T10:30:00'),
(6, 'Luxurious Audi Q7', 'GASOLINE', 'https://carsales.pxcrush.net/carsales/cars/dealer/blb6w8t7uwn2ekxgpc3dmzupz.jpg?pxc_method=fitfill&pxc_bgtype=self&pxc_size=720,480', 70000, 42000, 'AUTOMATIC', 2021, 5, 1, '2024-06-06T10:30:00'),
(7, 'Audi TT Coupe', 'GASOLINE', 'https://imgcdn.oto.com/large/gallery/color/1/10/audi-tt-coupe-color-835430.jpg', 50000, 28000, 'MANUAL', 2020, 6, 2, '2024-06-05T10:30:00'),
(8, 'Audi Q7 Premium Edition', 'DIESEL', 'https://imagecdnsa.zigwheels.ae/large/gallery/color/2/1968/audi-q7-color-792761.jpg', 25000, 49000, 'AUTOMATIC', 2023, 5, 1, '2024-05-31T14:15:00'),

-- Mercedes Offers
(9, 'Comfortable C-Class', 'DIESEL', 'https://www.mbusa.com/content/dam/mb-nafta/us/myco/my25/c-class/class-page/series/2025-C-SEDAN-CT-1-6-01-DR.jpg', 60000, 30000, 'AUTOMATIC', 2022, 7, 1, '2024-06-04T10:30:00'),
(10, 'Powerful GLE SUV', 'GASOLINE', 'https://larte-design.com/storage/app/media/kits/mercedes/gle-63/colors/hyacinthe-red/mercedes-gle-v167-amg-body-kit-front-hyacinthe-red.webp', 40000, 58000, 'AUTOMATIC', 2022, 8, 2, '2024-06-03T10:30:00'),
(11, 'Sleek CLA Coupe', 'GASOLINE', 'https://vehicle-images.dealerinspire.com/stock-images/thumbnails/large/chrome/ff4269c57e98e6e99c42ac842381a16a.png', 30000, 32000, 'MANUAL', 2023, 9, 1, '2024-06-02T10:30:00'),
(12, 'Mercedes CLA low mileage', 'GASOLINE', 'https://www.mbusa.com/content/dam/mb-nafta/us/myco/my25/cla-class/byo-options/2025-AMG-CLA-COUPE-MP-026.jpg', 15000, 36000, 'MANUAL', 2023, 9, 2, '2024-05-30T10:45:00'),
(13, 'Used Mercedes C-Class, excellent service history', 'DIESEL', 'https://images.autox.com/uploads/cars/2021/05/mercedes-benz-amg-a35-limousine.jpg', 98000, 19000, 'AUTOMATIC', 2016, 7, 1, '2024-05-29T13:30:00');

ALTER TABLE offers
    ALTER COLUMN id RESTART WITH 14;

-- Comments
INSERT INTO comments (id, text_content, created, author_id, offer_id)
VALUES (1, 'Is this car still available?', '2024-06-10T10:30:00', 2, 1),
       (2, 'Great condition, I''m interested!', '2024-06-11T12:45:00', 1, 1),
       (3, 'Can you lower the price a bit?', '2024-06-12T09:00:00', 2, 2),
       (4, 'Does it come with warranty?', '2024-06-12T15:20:00', 1, 3),
       (5, 'Looks awesome!', '2024-06-13T17:30:00', 2, 4);

ALTER TABLE comments
    ALTER COLUMN id RESTART WITH 6;