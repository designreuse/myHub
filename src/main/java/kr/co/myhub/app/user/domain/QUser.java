package kr.co.myhub.app.user.domain;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Nullable;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;

/**
 * 
 * file   : QUser.java
 * date   : 2014. 5. 18.
 * author : jmpark
 * content: QUser is a Querydsl query type for User
 * https://github.com/sathya2013/EBootstrap/blob/master/src/main/java/com/me/bootstrap/querydsl/QUser.java
 * http://netframework.tistory.com/entry/13-queryDSL-Spring-Data-JPA
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 18.   kbtapjm     최초생성
 */
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 2344533619527000777L;
    
    private static final PathInits INITS = PathInits.DIRECT;
    
    public static final QUser user = new QUser("user");
    
    public final NumberPath<Long> userKey = createNumber("userKey", Long.class);
    
    public final StringPath userId = createString("userId");
    
    public final StringPath email = createString("email");
    
    public final StringPath password = createString("password");
    
    public final StringPath userName = createString("userName");
    
    public final StringPath birthday = createString("birthday");
    
    public final StringPath gender = createString("gender");
    
    public final StringPath phoneNo = createString("phoneNo");
    
    public final DateTimePath<java.util.Date> crtDt = createDateTime("crtDt", java.util.Date.class);
    
    public final DateTimePath<java.util.Date> modDt = createDateTime("modDt", java.util.Date.class);
    
    public final DateTimePath<java.util.Date> passwordModDt = createDateTime("passwordModDt", java.util.Date.class);
    
    public final StringPath lastPassword = createString("lastPassword");
    
    public final NumberPath<java.lang.Integer> loginFailCount = createNumber("loginFailCount", Integer.class);
    
    public final DateTimePath<java.util.Date> loginFailDt = createDateTime("loginFailDt", java.util.Date.class);
    
    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Class<? extends User> type, PathMetadata<?> metadata) {
        super(type, metadata);
    }
    
    public QUser(PathMetadata<?> metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    private QUser(Class<? extends User> type, PathMetadata<?> metadata,
            @Nullable PathInits inits) {
        super(type, metadata, inits);
    }

    private QUser(Class<? extends User> type, String variable) {
        super(type, variable);
    }
}
