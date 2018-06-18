/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { TrabajoMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix-detail.component';
import { TrabajoMySuffixService } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.service';
import { TrabajoMySuffix } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.model';

describe('Component Tests', () => {

    describe('TrabajoMySuffix Management Detail Component', () => {
        let comp: TrabajoMySuffixDetailComponent;
        let fixture: ComponentFixture<TrabajoMySuffixDetailComponent>;
        let service: TrabajoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TrabajoMySuffixDetailComponent],
                providers: [
                    TrabajoMySuffixService
                ]
            })
            .overrideTemplate(TrabajoMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrabajoMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrabajoMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TrabajoMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.trabajo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
