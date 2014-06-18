package kr.co.myhub.app.user.domain;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;
import javax.annotation.Nullable;

import kr.co.myhub.app.user.domain.UserAuth;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;

/**
 * 
 * file   : QUserAuth.java
 * date   : 2014. 6. 16.
 * author : jmpark
 * content: QUserAuth is a Querydsl query type for UserAuth
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 16.   kbtapjm     최초생성
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserAuth extends EntityPathBase<UserAuth> {

    private static final long serialVersionUID = -1918773051785988167L;
    
    private static final PathInits INITS = PathInits.DIRECT;
    
    public static final QUserAuth userAuth = new QUserAuth("userAuth");
    
    public final NumberPath<Long> userAuthKey = createNumber("userAuthKey", Long.class);
    
    public final StringPath email = createString("email");
    
    public final NumberPath<Long> priv = createNumber("priv", Long.class);
    
    public final DateTimePath<java.util.Date> crtDt = createDateTime("crtDt", java.util.Date.class);
    
    public final NumberPath<Long> userKey = createNumber("userKey", Long.class);
    
    public QUserAuth(String variable) {
        this(UserAuth.class, forVariable(variable), INITS);
    }

    public QUserAuth(Class<? extends UserAuth> type, PathMetadata<?> metadata) {
        super(type, metadata);
    }
    
    public QUserAuth(PathMetadata<?> metadata, PathInits inits) {
        this(UserAuth.class, metadata, inits);
    }

    private QUserAuth(Class<? extends UserAuth> type, PathMetadata<?> metadata,
            @Nullable PathInits inits) {
        super(type, metadata, inits);
    }

    private QUserAuth(Class<? extends UserAuth> type, String variable) {
        super(type, variable);
    }
}
